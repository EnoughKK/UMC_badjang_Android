package com.umc.badjang.PostWritePage

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.umc.badjang.ApplicationClass
import com.umc.badjang.HomePagaApi.MainApiClient
import com.umc.badjang.HomePagaApi.MainMySchoolApiData
import com.umc.badjang.HomePagaApi.MainMySchoolApiResult
import com.umc.badjang.HomePagaApi.MainMySchoolApiService
import com.umc.badjang.databinding.FragmentPostWriteBinding
import com.umc.badjang.mConnectUserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 게시글 작성 페이지
class PostWriteFragment : Fragment() {
    private lateinit var viewBinding: FragmentPostWriteBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    // 제목, 내용 글자수
    private var titleLength: Int = 0
    private var contentLength: Int = 0

    // 게시판 카테고리
    private var categoryIdx: Int? = null
    private var category: String? = null

    // 선택된 이미지
    private var selectImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPostWriteBinding.inflate(layoutInflater)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 로그인 된 사용자 idx, jwt 조회
        mConnectUserId = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 이전 페이지에서 카테고리 데이터 받아오기
        //categoryIdx = requireArguments().getInt("category_idx", 0)
        //category = requireArguments().getString("category_name", "null")

        //if(category == null || category == "null")
        category = "자유게시판"

        // 이전 버튼 선택 시
        viewBinding.postWriteBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 제목 입력 글자 수 제한
        viewBinding.postWriteTitle.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력 변화 감지
                //Log.d("Test", viewBinding.postWriteTitle.text.length.toString())
                titleLength = viewBinding.postWriteTitle.text.length
                viewBinding.postWriteTitleCount.text = titleLength.toString() + "/50"

                // 글자 수 제한을 넘어가면
                if(titleLength > 50) {
                    // 글자 수 표시를 빨간색으로
                    viewBinding.postWriteTitleCount.setTextColor(Color.parseColor("#ED1C24"))

                    // 작성완료 버튼 비활성화 - 색 변경
                    viewBinding.postWriteBtn.setTextColor(Color.parseColor("#BBD7FF"))

                    // Toast 메시지 출력
                    Toast.makeText(requireActivity(), "게시글 제목은 최대 50자까지 작성 가능합니다.", Toast.LENGTH_SHORT).show()
                }
                // 글자 수 제한을 넘어가지 않으면
                else {
                    // 글자 수 표시를 회색으로
                    viewBinding.postWriteTitleCount.setTextColor(Color.parseColor("#696969"))

                    // 작성완료 버튼 활성화 - 색 변경
                    viewBinding.postWriteBtn.setTextColor(Color.parseColor("#FFFFFF"))
                }
            }
        })

        // 내용 입력 글자 수 제한
        viewBinding.postWriteContent.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력 변화 감지
                //Log.d("Test", viewBinding.postWriteContent.text.length.toString())
                contentLength = viewBinding.postWriteContent.text.length
                viewBinding.postWriteContentCount.text = contentLength.toString() + "/500"

                // 글자 수 제한을 넘어가면
                if(contentLength > 500) {
                    // 글자 수 표시를 빨간색으로
                    viewBinding.postWriteContentCount.setTextColor(Color.parseColor("#ED1C24"))

                    // 작성완료 버튼 비활성화 - 색 변경
                    viewBinding.postWriteBtn.setTextColor(Color.parseColor("#BBD7FF"))

                    // Toast 메시지 출력
                    Toast.makeText(requireActivity(), "게시글 내용은 최대 500자까지 작성 가능합니다.", Toast.LENGTH_SHORT).show()
                }
                // 글자 수 제한을 넘어가지 않으면
                else {
                    // 글자 수 표시를 회색으로
                    viewBinding.postWriteContentCount.setTextColor(Color.parseColor("#696969"))

                    // 작성완료 버튼 활성화 - 색 변경
                    viewBinding.postWriteBtn.setTextColor(Color.parseColor("#FFFFFF"))
                }
            }
        })

        // 이미지 추가 버튼 클릭 시
        viewBinding.postWriteAddImage.setOnClickListener {
            navigatePhotos()
        }

        // 작성 완료 버튼 선택 시
        viewBinding.postWriteBtn.setOnClickListener {
            // 제목, 내용 글자수가 올바르면
            if(checkCounts()) {
                // 제목, 내용
                val title = viewBinding.postWriteTitle.text.toString()
                val content = viewBinding.postWriteContent.text.toString()

                // 익명 여부
                var anonymity = "N"
                if(viewBinding.anonymityCheckbox.isChecked) anonymity = "Y"

                val body = PostWriteApiBody(
                    mConnectUserId!!,
                    category!!,
                    title,
                    content,
                    selectImage,
                    anonymity
                )

                Log.d("Test", body.toString())

                // 게시글 작성
                //apiAddPost(body)

                // 이전 페이지로 이동
                //requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
                //requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    // 제목, 내용 글자수 체크
    private fun checkCounts(): Boolean {
        // 제목이 없거나 50자가 넘는 경우
        if(titleLength == 0 || titleLength > 50) return false
        // 내용이 없거나 500자가 넘는 경우
        if(contentLength == 0 || contentLength > 500) return false

        return true
    }

    // 갤러리 오픈
    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    // 갤러리에서 호출한 액티비티 결과 반환
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            Log.d("msg", "gallery error")
            return
        }
        when (requestCode) {
            2000 -> {
                val selectedImageURI: Uri? = data?.data
                if (selectedImageURI != null) {
                    // ImageView에 선택한 이미지 넣기
                    viewBinding.postWriteImageview.setImageURI(selectedImageURI)
                    // ImageView 보임
                    viewBinding.postWriteImageview.visibility = View.VISIBLE

                    // 사용자 디바이스에서 받은 이미지 drawable 파일
                    val imgDrawable = viewBinding.postWriteImageview.drawable

                    // drawable 파일을 bitmap으로 변환
                    val bitmapDrawable = imgDrawable as BitmapDrawable
                    val bitmap = bitmapDrawable.bitmap

                    // bitmap을 String으로 변환
                    val converter = BitmapConverter()
                    selectImage = converter.bitmapToString(bitmap)

                    //Log.d("Test", selectImage.toString())
                } else {
                    Log.d("msg", "gallery error")
                }
            }
            else -> {
                Log.d("msg", "gallery error")
            }
        }
    }

    // 게시글 작성
    private fun apiAddPost(post: PostWriteApiBody) {
        retrofit!!.create(PostWriteApiService::class.java).addPost(xAccessToken=jwt!!, userIdx=mConnectUserId!!, params=post)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d(ContentValues.TAG,"게시글 작성 -------------------------------------------")
                    Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d(ContentValues.TAG,"게시글 작성 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

}