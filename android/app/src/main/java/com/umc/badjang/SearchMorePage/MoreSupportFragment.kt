package com.umc.badjang.SearchMorePage

import com.umc.badjang.HomeMorePage.NationalNewsAdapter
import com.umc.badjang.HomeMorePage.NationalNewsData
import com.umc.badjang.HomeMorePage.NationalNewsDataBitmap
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
import com.umc.badjang.HomePage.MainMySchoolData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentMySchoolBinding
import com.umc.badjang.mConnectUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 검색화면 > 장학금
class  MoreSupportFragment : Fragment() {
    private lateinit var viewBinding: FragmentMySchoolBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    // 우리학교 장학금 recyclerview adapter
    private val mySchoolDatas = mutableListOf<NationalNewsDataBitmap>()
    private lateinit var mySchoolAdapter: NationalNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMySchoolBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 이전 버튼 선택 시
        viewBinding.mySchoolBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // recyclerview 세팅
        initRecycler()

        // 우리 학교 장학금 api
        apiMainMySchool()
    }

    // 우리학교 recyclerview 세팅
    private fun initRecycler() {
        mySchoolAdapter = NationalNewsAdapter(
            requireContext(),
            onClickScholarshipBookmark = {
                apiBookmarkMySchool(it)
            },
            onClickSupportBookmark = {
                null
            })
        viewBinding.mySchoolRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.mySchoolRecyclerview.adapter = mySchoolAdapter
        mySchoolAdapter.datas = mySchoolDatas
    }

    // 우리학교 데이터 추가
    private fun addMySchoolData(mySchoolData: NationalNewsData) {
        if(mySchoolData.nationalNewsImg == null) {
            mySchoolDatas.apply {
                add(
                    NationalNewsDataBitmap(
                    mySchoolData.scholarshipIdx,
                    mySchoolData.supportIdx,
                    mySchoolData.nationalNewsInstitution,
                    mySchoolData.nationalNewsTitle,
                    mySchoolData.nationalNewsContent,
                    null,
                    mySchoolData.nationalNewsCommentsCnt,
                    mySchoolData.nationalNewsViewCnt)
                )
            }
            mySchoolAdapter.notifyDataSetChanged()
        }

        else {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap? = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(mySchoolData.nationalNewsImg)
                }
                mySchoolDatas.apply {
                    add(
                        NationalNewsDataBitmap(
                        mySchoolData.scholarshipIdx,
                        mySchoolData.supportIdx,
                        mySchoolData.nationalNewsInstitution,
                        mySchoolData.nationalNewsTitle,
                        mySchoolData.nationalNewsContent,
                        img,
                        mySchoolData.nationalNewsCommentsCnt,
                        mySchoolData.nationalNewsViewCnt
                    )
                    )
                }

                mySchoolAdapter.notifyDataSetChanged()
            }
        }
    }

    // 우리학교 장학금 즐겨찾기 추가 및 취소 api
    private fun apiBookmarkMySchool(scholarshipIdx: Int) {
        retrofit!!.create(BookmarkScholarshipApiService::class.java)
            .bookmarkScholarship(xAccessToken=jwt!!, scholarshipIdx=scholarshipIdx)
            .enqueue(object : Callback<BookmarkResponseApiData> {
                override fun onResponse(call: Call<BookmarkResponseApiData>, response: Response<BookmarkResponseApiData>) {
                    Log.d(ContentValues.TAG,"우리학교 장학금 즐겨찾기 추가 및 취소 -------------------------------------------")
                    Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                }

                override fun onFailure(call: Call<BookmarkResponseApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"우리학교 장학금 즐겨찾기 추가 및 취소 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 우리학교 장학금 조회 api
    private fun apiMainMySchool() {
        val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
        retrofit!!.create(MainMySchoolApiService::class.java).getMainMySchool(xAccessToken=jwt!!, userIdx= mConnectUserId!!)
            .enqueue(object : Callback<MainMySchoolApiData> {
                override fun onResponse(call: Call<MainMySchoolApiData>, response: Response<MainMySchoolApiData>) {
                    //Log.d(TAG,"우리학교 장학금 -------------------------------------------")
                    //Log.d(TAG, "onResponse: ${response.body().toString()}")

                    var allMySchoolData: MainMySchoolApiData = response.body()!!
                    var mySchoolData: MutableList<MainMySchoolApiResult> = allMySchoolData.result

                    // 우리학교 장학금 정보의 장학금 idx로 각 장학금 정보 조회
                    for(i:Int in (0..mySchoolData.size - 1)) {
                        //if(i >= 4) break
                        apiScholarship(mySchoolData[i].scholarship_idx.toLong())
                    }
                }

                override fun onFailure(call: Call<MainMySchoolApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"우리학교 장학금 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 장학금 idx로 장학금 정보 조회 api
    private fun apiScholarship(scholarshipIdx: Long) {
        retrofit!!.create(ScholarshipIdxApiService::class.java).getScholarshipData(scholarshipIdx)
            .enqueue(object : Callback<ScholarshipIdxApiData> {
                override fun onResponse(call: Call<ScholarshipIdxApiData>, response: Response<ScholarshipIdxApiData>) {
                    //Log.d(TAG,"장학금 조회 -------------------------------------------")
                    //Log.d(TAG, "onResponse: ${response.body().toString()}")

                    var scholarshipData = response.body()!!.result

                    addMySchoolData(
                        NationalNewsData(
                            scholarshipData.scholarship_idx.toInt(),
                            null,
                            scholarshipData.scholarship_institution,
                            scholarshipData.scholarship_name,
                            scholarshipData.scholarship_content,
                            scholarshipData.scholarship_image,
                            scholarshipData.scholarship_comment,
                            scholarshipData.scholarship_view
                        )
                    )
                }

                override fun onFailure(call: Call<ScholarshipIdxApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"장학금 조회 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }
}