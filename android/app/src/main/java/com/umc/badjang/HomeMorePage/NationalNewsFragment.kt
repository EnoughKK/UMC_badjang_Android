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
import com.umc.badjang.HomePage.HomeFragment
import com.umc.badjang.HomePage.MainNationalNewsData
import com.umc.badjang.HomePage.MainNationalNewsDataBitmap
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentNationalNewsBinding
import com.umc.badjang.mConnectUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

// 홈화면 > 전국소식
class NationalNewsFragment : Fragment() {
    private lateinit var viewBinding: FragmentNationalNewsBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    // 정렬을 위한 전국소식 리스트
    private var allNationalNewsList: MutableList<NationalNewsList>? = null

    // 전국소식 리스트 recyclerview adapter
    private val nationalNewsDatas = mutableListOf<NationalNewsDataBitmap>()
    private lateinit var nationalNewsAdapter: NationalNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNationalNewsBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // recyclerview 세팅
        initRecycler()

        // 이전 버튼 선택 시
        viewBinding.nationalNewsBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 전국소식 api
        apiMainNationalNews()
    }

    // 전국소식 recyclerview 세팅
    private fun initRecycler() {
        nationalNewsAdapter = NationalNewsAdapter(
            requireContext(),
            onClickScholarshipBookmark = {
                apiBookmarkScholarship(it)
            },
            onClickSupportBookmark = {
                apiBookmarkSupport(it)
            })
        viewBinding.nationalNewsRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.nationalNewsRecyclerview.adapter = nationalNewsAdapter
        nationalNewsAdapter.datas = nationalNewsDatas
    }

    // 전국소식 데이터 추가
    private fun addNationalNewsData(nationalNews: NationalNewsData) {
        if(nationalNews.nationalNewsImg == null) {
            nationalNewsDatas.apply {
                add(NationalNewsDataBitmap(
                    nationalNews.scholarshipIdx,
                    nationalNews.supportIdx,
                    nationalNews.nationalNewsInstitution,
                    nationalNews.nationalNewsTitle,
                    nationalNews.nationalNewsContent,
                    null,
                    nationalNews.nationalNewsCommentsCnt,
                    nationalNews.nationalNewsViewCnt,
                    nationalNews.bookmarkCheck
                ))
            }
            nationalNewsAdapter.notifyDataSetChanged()
        }

        else {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap? = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(nationalNews.nationalNewsImg)
                }
                nationalNewsDatas.apply {
                    add(NationalNewsDataBitmap(
                        nationalNews.scholarshipIdx,
                        nationalNews.supportIdx,
                        nationalNews.nationalNewsInstitution,
                        nationalNews.nationalNewsTitle,
                        nationalNews.nationalNewsContent,
                        img,
                        nationalNews.nationalNewsCommentsCnt,
                        nationalNews.nationalNewsViewCnt,
                        nationalNews.bookmarkCheck
                    ))
                }

                nationalNewsAdapter.notifyDataSetChanged()
            }
        }
    }

    // 전국소식 장학금 즐겨찾기 추가 및 취소 api
    private fun apiBookmarkScholarship(position: Int) {
        retrofit!!.create(BookmarkScholarshipApiService::class.java)
            .bookmarkScholarship(xAccessToken=jwt!!, scholarshipIdx=nationalNewsDatas[position].scholarshipIdx!!)
            .enqueue(object : Callback<BookmarkResponseApiData> {
                override fun onResponse(call: Call<BookmarkResponseApiData>, response: Response<BookmarkResponseApiData>) {
                    Log.d(ContentValues.TAG,"전국소식 장학금 즐겨찾기 추가 및 취소 -------------------------------------------")
                    Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    nationalNewsDatas[position].bookmarkCheck = !nationalNewsDatas[position].bookmarkCheck
                    nationalNewsAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<BookmarkResponseApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"전국소식 장학금 즐겨찾기 추가 및 취소 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 전국소식 지원금 즐겨찾기 추가 및 취소 api
    private fun apiBookmarkSupport(supportIdx: Int) {
        Log.d("지원금 즐겨찾기", "---------------------------")
        Log.d("지원금 즐겨찾기", supportIdx.toString())
    }

    // 전국소식 조회 api
    private fun apiMainNationalNews() {
        retrofit!!.create(MainNationalNewsApiService::class.java).getMainNationalNews(mConnectUserId!!)
            .enqueue(object : Callback<MainNationalNewsApiData> {
                override fun onResponse(call: Call<MainNationalNewsApiData>, response: Response<MainNationalNewsApiData>) {
                    Log.d(ContentValues.TAG,"전국소식 -------------------------------------------")

                    allNationalNewsList = mutableListOf<NationalNewsList>()

                    var allNationalNewsData: MainNationalNewsApiData = response.body()!!
                    var scholarshipData: MutableList<MainNationalNewsScholarship> = allNationalNewsData.result.scholarshipList
                    var supportData: MutableList<MainNationalNewsSupport> = allNationalNewsData.result.supportList

                    for(i:Int in (0..scholarshipData.size - 1)) {
                        apiCheckNationalNewsBookmark(
                            scholarshipData[i].scholarship_idx,
                            scholarshipData[i].scholarship_institution,
                            scholarshipData[i].scholarship_name,
                            scholarshipData[i].scholarship_content,
                            scholarshipData[i].scholarship_image,
                            scholarshipData[i].scholarship_comment,
                            scholarshipData[i].scholarship_view,
                            scholarshipData[i].scholarship_createAt
                        )
                    }
                    for(i:Int in (0..supportData.size - 1)) {
                        addNationalNewsData(
                            NationalNewsData(
                                null,
                                supportData[i].support_idx,
                                supportData[i].support_institution,
                                supportData[i].support_name,
                                supportData[i].support_content,
                                supportData[i].support_image,
                                supportData[i].support_comment,
                                supportData[i].support_view,
                                false
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<MainNationalNewsApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"-------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 전국소식 즐겨찾기 유무 체크
    private fun apiCheckNationalNewsBookmark(
        scholarshipIdx: Int?, scholarshipInstitution: String?, scholarshipName: String, scholarshipContent: String?,
        scholarshipImage: String?, scholarshipComment: Int, scholarshipView: Int, createAt: String) {

        retrofit!!.create(CheckScholarshipBookmarkApiService::class.java)
            .checkScholarshipBookmark(xAccessToken=jwt!!, scholarshipIdx=scholarshipIdx!!)
            .enqueue(object : Callback<CheckScholarshipBookmarkApiData> {
                override fun onResponse(call: Call<CheckScholarshipBookmarkApiData>, response: Response<CheckScholarshipBookmarkApiData>) {
                    //Log.d(ContentValues.TAG,"전국소식 즐겨찾기 -------------------------------------------")
                    //Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    var checkBookmark = false
                    if(response.body()!!.result.bookmark_check == "Y") checkBookmark = true

                    addNationalNewsData(
                        NationalNewsData(
                            scholarshipIdx,
                            null,
                            scholarshipInstitution,
                            scholarshipName,
                            scholarshipContent,
                            scholarshipImage,
                            scholarshipComment,
                            scholarshipView,
                            checkBookmark
                        )
                    )

                    // 생성일을 기준으로 정렬
                    //Collections.sort(allNationalNewsList)

                    // 최신순
                    /*for (i:Int in (0..allNationalNewsList!!.size - 1)) {
                        //if(i >= 5) break
                        addNationalNewsData(allNationalNewsList!![i].mNationalNewsData)
                    }*/

                }

                override fun onFailure(call: Call<CheckScholarshipBookmarkApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"전국소식 즐겨찾기 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 전국소식 생성일 순으로 정렬
    internal class NationalNewsList(mNationalNewsData: NationalNewsData, createAt: String) :
        Comparable<NationalNewsList> {

        val mNationalNewsData: NationalNewsData
        private val createAt: String

        override operator fun compareTo(nationalNews: NationalNewsList): Int {
            if (nationalNews.createAt < createAt) {
                return 1
            } else if (nationalNews.createAt > createAt) {
                return -1
            }
            return 0
        }

        init {
            this.mNationalNewsData = mNationalNewsData
            this.createAt = createAt
        }
    }
}