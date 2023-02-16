package com.umc.badjang.HomeMorePage

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.HomePagaApi.*
import com.umc.badjang.HomePage.MainMySchoolAdapter
import com.umc.badjang.HomePage.MainMySchoolData
import com.umc.badjang.HomePage.MainPopularData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentPopularPostBinding
import com.umc.badjang.mConnectUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 홈화면 > 인기글
class PopularPostFragment : Fragment() {
    private lateinit var viewBinding: FragmentPopularPostBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 인기글 리스트 recyclerview adapter
    private val popularPostDatas = mutableListOf<PopularPostData>()
    private lateinit var popularPostAdapter: PopularPostAdapter

    // 인기글 데이터 익명 사용자 프로필 사진
    var profileImg: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPopularPostBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mConnectUserId = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)

        // recyclerview 세팅
        initRecycler()

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 임시 데이터
        profileImg = BitmapFactory.decodeResource(resources, R.drawable.non_profile)

        // 인기글 조회 api
        apiMainPopular()

        // 이전 버튼 선택 시
        viewBinding.popularPostBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    // 인기글 recyclerview 세팅
    private fun initRecycler() {
        popularPostAdapter = PopularPostAdapter(requireContext())
        viewBinding.popularPostRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.popularPostRecyclerview.adapter = popularPostAdapter
        popularPostAdapter.datas = popularPostDatas
    }

    // 인기글 데이터 추가
    private fun addPopularPostData(popularPost: PopularPostData) {
        popularPostDatas.apply {
            add(popularPost)
        }
        popularPostAdapter.notifyDataSetChanged()
    }

    // 인기글 조회 api
    private fun apiMainPopular() {
        retrofit!!.create(MainPopularApiService::class.java).getMainPopular()
            .enqueue(object : Callback<MainPopularApiData> {
                override fun onResponse(call: Call<MainPopularApiData>, response: Response<MainPopularApiData>) {
                    //Log.d(ContentValues.TAG,"인기글 -------------------------------------------")
                    //Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    var allPopularData: MainPopularApiData = response.body()!!
                    var popularData: MutableList<MainPopularApiResult> = allPopularData.result

                    for(i: Int in (0..popularData.size - 1)) {
                        apiPostData(popularData[i].post_idx)
                    }

                }

                override fun onFailure(call: Call<MainPopularApiData>, t: Throwable) {
                    //Log.d(TAG,"-------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 게시글 조회 api
    private fun apiPostData(postIdx: Int) {
        retrofit!!.create(PostIdxApiService::class.java).getPostData(mConnectUserId!!.toInt(), postIdx)
            .enqueue(object : Callback<PostIdxApiData> {
                override fun onResponse(call: Call<PostIdxApiData>, response: Response<PostIdxApiData>) {
                    //Log.d(ContentValues.TAG,"인기글 내용 -------------------------------------------")
                    //Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    val postData = response.body()!!.result[0]

                    // 익명
                    if(postData.post_anonymity == "Y") {
                        // 이미지 있음
                        if(postData.post_image != null) {
                            CoroutineScope(Dispatchers.Main).launch {
                                val img: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.post_image)
                                }
                                addPopularPostData(
                                    PopularPostData(
                                        profileImg!!,
                                        "익명",
                                        postData.post_createAt,
                                        postData.post_name,
                                        postData.post_content,
                                        img,
                                        postData.post_comment,
                                        postData.post_view,
                                        postData.post_recommend
                                    )
                                )
                            }
                        }
                        // 이미지 없음
                        else {
                            addPopularPostData(
                                PopularPostData(
                                    profileImg!!,
                                    "익명",
                                    postData.post_createAt,
                                    postData.post_name,
                                    postData.post_content,
                                    null,
                                    postData.post_comment,
                                    postData.post_view,
                                    postData.post_recommend
                                )
                            )
                        }
                    }
                    // 익명 아님
                    else {
                        // 이미지 있음
                        if(postData.post_image != null) {
                            CoroutineScope(Dispatchers.Main).launch {
                                val profile: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.user_profileimage_url!!)
                                }
                                val img: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.post_image)
                                }
                                addPopularPostData(
                                    PopularPostData(
                                        profile!!,
                                        postData.user_name,
                                        postData.post_createAt,
                                        postData.post_name,
                                        postData.post_content,
                                        img,
                                        postData.post_comment,
                                        postData.post_view,
                                        postData.post_recommend
                                    )
                                )
                            }
                        }
                        // 이미지 없음
                        else {
                            CoroutineScope(Dispatchers.Main).launch {
                                val profile: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.user_profileimage_url!!)
                                }
                                addPopularPostData(
                                    PopularPostData(
                                        profile!!,
                                        postData.user_name,
                                        postData.post_createAt,
                                        postData.post_name,
                                        postData.post_content,
                                        null,
                                        postData.post_comment,
                                        postData.post_view,
                                        postData.post_recommend
                                    )
                                )
                            }
                        }
                    }

                    /*CoroutineScope(Dispatchers.Main).launch {
                        val img: Bitmap? = withContext(Dispatchers.IO) {
                            ImageLoader.loadImage(postData.post_image!!)
                        }
                    }*/
                }

                override fun onFailure(call: Call<PostIdxApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"인기글 내용 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }
}