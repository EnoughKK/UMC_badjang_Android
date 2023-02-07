package com.umc.badjang.HomePage

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import com.umc.badjang.ApplicationClass
import com.umc.badjang.Bookmarks.BookmarksFragment
import com.umc.badjang.HomeMorePage.*
import com.umc.badjang.HomePagaApi.*
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentHomeBinding
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

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    val mySchoolSampleData = mutableListOf(mutableListOf("재학생 장학금 - 개척", 13), mutableListOf("재학생 장학금 - 희망,경상국립대학교", 8),
        mutableListOf("2023학년도 1학기 특별장학금 신청 안내", 23), mutableListOf("2023년 동암장학회 장학생 선발 안내 ", 11))
    val nationalNewsSampleData = mutableListOf(//mutableListOf("★★ 2023학년도 1학기 국가장학금 2차 신청 안내 ★★", "https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/2023-1%ED%95%99%EA%B8%B0%20%EA%B5%AD%EA%B0%80%EC%9E%A5%ED%95%99%EA%B8%88%202%EC%B0%A8%20%EC%8B%A0%EC%B2%AD%EC%95%88%EB%82%B4%20%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg?alt=media&token=84d810f1-28af-45ef-aee8-98177fe7a965"),
        //mutableListOf("충주 청년관광코디네이터 육성 사업", "https://firebasestorage.googleapis.com/v0/b/badjang-88139.appspot.com/o/%EC%B6%A9%EB%B6%81.png?alt=media&token=caa10371-0dc2-4017-9b36-94c562f53dbc"),
        mutableListOf("청년테마별 취업지원", "https://www.jobaba.net/resource/images/web/2018/01/02/1514871744928_6915623648.png"),
        mutableListOf("2023학년도 재단법인 플라톤 아카데미 인문 지혜 장학생 판플러스(PAN+) 모집 안내", "https://cyberimg.wku.ac.kr/ComBoard/img/upload/1115983888724/1115985252888/2023/01/1675061273364/org/bbs1.jpg"),
        mutableListOf("2023년도 대만장학금 장학생 선발 안내", "https://www.pknu.ac.kr/images/front/sub/univ_logo00.png"))

    // 추천 배너 슬라이더 adapter
    private lateinit var mainRecommendSliderAdapter: MainRecommendSliderAdapter
    private var mainRecommendCurrentPosition = 0 // 슬라이드 이미지 현재 위치

    // 추천 배너 슬라이드 핸들러 설정
    val handler = Handler(Looper.getMainLooper()){
        setMainRecommendSlideImage()
        true
    }

    // 우리대학 장학금 리스트 recyclerview adapter
    private val mySchoolDatas = mutableListOf<MainMySchoolData>()
    private lateinit var mainMySchoolAdapter: MainMySchoolAdapter

    // 인기글 리스트 recyclerview adapter
    private val popularDatas = mutableListOf<MainPopularData>()
    private lateinit var mainPopularAdapter: MainPopularAdapter

    // 전국소식 리스트 recyclerview adapter
    private val nationalNewsDatas = mutableListOf<MainNationalNewsDataBitmap>()
    private lateinit var mainNationalNewsAdapter: MainNationalNewsAdapter

    // 프래그먼트 전환을 위해
    var activity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 로그인 된 사용자 idx 조회
        mConnectUserId = ApplicationClass.prefs.getString("user_idx", "0").toInt()

        // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar) //커스텀한 toolbar를 액션바로 사용

        // 우리학교 장학금 더보기 버튼 선택
        viewBinding.mainMySchoolMore.setOnClickListener(View.OnClickListener {
            activity?.changeFragment(MySchoolFragment())
        })

        // 인기글 더보기 버튼 선택
        viewBinding.mainPopularMore.setOnClickListener(View.OnClickListener {
            activity?.changeFragment(PopularPostFragment())
        })

        // 전국소식 더보기 버튼 선택
        viewBinding.mainNationalNewsMore.setOnClickListener(View.OnClickListener {
            activity?.changeFragment(NationalNewsFragment())
        })

        // 즐겨찾기 버튼 선택
        val bookmarksBtn: ImageButton = toolbar.findViewById(R.id.toolbar_star_btn)
        bookmarksBtn.setOnClickListener {
            activity?.changeFragment(BookmarksFragment())
        }

        // 알림 버튼 선택
        val newIssueBtn: ImageButton = toolbar.findViewById(R.id.toolbar_bell_btn)
        newIssueBtn.setOnClickListener {
            activity?.changeFragment(NewIssueFragment())
        }

        // 학교 필터 Floating 버튼 선택
        viewBinding.mainSchoolFilterBtn.setOnClickListener {
            SchoolFilterDialog(requireContext()).show()
        }

        // 추천 배너 슬라이드 어댑터 연결하기
        mainRecommendSliderAdapter = MainRecommendSliderAdapter()
        viewBinding.mainRecommendSlideViewpager.adapter = mainRecommendSliderAdapter

        // 추천 배너 슬라이드 ViewPager 넘기는 쓰레드
        val mainRecommendSlideThread = Thread(mainRecommendSlideRunnable())
        mainRecommendSlideThread.start()

        // 추천 배너 슬라이드 ViewPager의 Indicator
        val mainRecommendSlideIndicator: WormDotsIndicator = viewBinding.mainRecommendSlideIndicator
        mainRecommendSlideIndicator.attachTo(viewBinding.mainRecommendSlideViewpager)

        // recyclerview 세팅
        initRecycler()

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 인기글 조회 api
        apiMainPopular()

        // 전국소식 조회 api
        apiMainNationalNews()

        // 우리학교 장학금 데이터 추가
        /*for(i: Int in 0..(mySchoolSampleData.size - 1)) {
            addMySchoolData(MainMySchoolData(i+1, mySchoolSampleData[i][0].toString(), mySchoolSampleData[i][1]))
        }*/
        addMySchoolData(MainMySchoolData(1, mySchoolSampleData[0][0].toString(), 13))
        addMySchoolData(MainMySchoolData(2, mySchoolSampleData[1][0].toString(), 8))
        addMySchoolData(MainMySchoolData(3, mySchoolSampleData[2][0].toString(), 23))
        addMySchoolData(MainMySchoolData(4, mySchoolSampleData[3][0].toString(), 11))

        // 인기글 데이터 추가
        //for(i: Int in 0..3) {
        //    addPopularData(MainPopularData(i+1, "자기추천장학금 신청방법", 65,215))
        //}

        // 전국 소식 데이터 추가
        //val img: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.national_news_img1)
        /*for(i: Int in 0..(nationalNewsSampleData.size - 1)) {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(nationalNewsSampleData[i][1].toString())!!
                }
                addNationalNewsData(MainNationalNewsData(img, nationalNewsSampleData[i][0].toString()))
            }
        }*/
    }

    // 추천 배너 슬라이드 이미지 변경하기
    fun setMainRecommendSlideImage(){
        if(mainRecommendCurrentPosition == 3) mainRecommendCurrentPosition = 0
        viewBinding.mainRecommendSlideViewpager.setCurrentItem(mainRecommendCurrentPosition,true)
        mainRecommendCurrentPosition += 1
    }

    // 추천 배너 슬라이드 5초마다 이미지 넘기기
    inner class mainRecommendSlideRunnable: Runnable{
        override fun run() {
            while(true){
                Thread.sleep(5000)
                handler.sendEmptyMessage(0)
            }
        }
    }

    // recyclerview 세팅
    private fun initRecycler() {
        // 우리학교 장학금 recyclerview 세팅
        mainMySchoolAdapter = MainMySchoolAdapter(requireContext())
        viewBinding.mainMySchoolRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.mainMySchoolRecyclerview.adapter = mainMySchoolAdapter
        mainMySchoolAdapter.datas = mySchoolDatas

        // 인기글 recyclerview 세팅
        mainPopularAdapter = MainPopularAdapter(requireContext())
        viewBinding.mainPopularRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.mainPopularRecyclerview.adapter = mainPopularAdapter
        mainPopularAdapter.datas = popularDatas

        // 전국 소식 recyclerview 세팅
        mainNationalNewsAdapter = MainNationalNewsAdapter(requireContext())
        viewBinding.mainNationalNewsRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewBinding.mainNationalNewsRecyclerview.adapter = mainNationalNewsAdapter
        mainNationalNewsAdapter.datas = nationalNewsDatas
    }

    // 우리학교 장학금 데이터 추가
    private fun addMySchoolData(mainMySchoolPost: MainMySchoolData) {
        mySchoolDatas.apply {
            add(mainMySchoolPost)
        }
        mainMySchoolAdapter.notifyDataSetChanged()
    }

    // 인기글 데이터 추가
    private fun addPopularData(mainPopularPost: MainPopularData) {
        popularDatas.apply {
            add(mainPopularPost)
        }
        mainPopularAdapter.notifyDataSetChanged()
    }

    // 전국 소식 데이터 추가
    private fun addNationalNewsData(mainNationalNewsPost: MainNationalNewsData) {

        if(mainNationalNewsPost.nationalNewsImage == null) {
            nationalNewsDatas.apply {
                add(MainNationalNewsDataBitmap(null, mainNationalNewsPost.nationalNewsTitle))
            }
            mainNationalNewsAdapter.notifyDataSetChanged()
        }

        else {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(mainNationalNewsPost.nationalNewsImage!!)!!
                }
                nationalNewsDatas.apply {
                    add(MainNationalNewsDataBitmap(img, mainNationalNewsPost.nationalNewsTitle))
                }

                mainNationalNewsAdapter.notifyDataSetChanged()
            }
        }
    }

    // 인기글 조회 api
    private fun apiMainPopular() {
        retrofit!!.create(MainPopularApiService::class.java).getMainPopular()
            .enqueue(object : Callback<MainPopularApiData> {
                override fun onResponse(call: Call<MainPopularApiData>, response: Response<MainPopularApiData>) {
                    // Log.d(TAG,"인기글 -------------------------------------------")
                    // Log.d(TAG, "onResponse: ${response.body().toString()}")

                    var allPopularData: MainPopularApiData = response.body()!!
                    var popularData: MutableList<MainPopularApiResult> = allPopularData.result
                    for(i:Int in (0..popularData.size - 1)) {
                        if(i >= 4) break
                        addPopularData(MainPopularData(i+1, popularData[i].post_content, i,(i*7+1)%10))
                    }

                }

                override fun onFailure(call: Call<MainPopularApiData>, t: Throwable) {
                    //Log.d(TAG,"-------------------------------------------")
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 전국소식 조회 api
    private fun apiMainNationalNews() {
        retrofit!!.create(MainNationalNewsApiService::class.java).getMainNationalNews(mConnectUserId!!)
            .enqueue(object : Callback<MainNationalNewsApiData> {
                override fun onResponse(call: Call<MainNationalNewsApiData>, response: Response<MainNationalNewsApiData>) {
                    Log.d(TAG,"전국소식 -------------------------------------------")

                    val allNationalNewsList = mutableListOf<AllNationalNewsList>()

                    var allNationalNewsData: MainNationalNewsApiData = response.body()!!
                    var scholarshipData: MutableList<MainNationalNewsScholarship> = allNationalNewsData.result.scholarshipList
                    var supportData: MutableList<MainNationalNewsSupport> = allNationalNewsData.result.supportList

                    for(i:Int in (0..scholarshipData.size - 1)) {
                        //if(i >= 5) break
                        allNationalNewsList.add(AllNationalNewsList(
                            MainNationalNewsData(scholarshipData[i].scholarship_image, scholarshipData[i].scholarship_name),
                            scholarshipData[i].scholarship_createAt))
                    }
                    for(i:Int in (0..supportData.size - 1)) {
                        allNationalNewsList.add(AllNationalNewsList(
                            MainNationalNewsData(scholarshipData[i].scholarship_image, supportData[i].support_name),
                            supportData[i].support_createAt))
                    }

                    // 생성일을 기준으로 정렬
                    Collections.sort(allNationalNewsList)

                    // 최신순 상위 5개
                    for (i:Int in (0..allNationalNewsList.size - 1)) {
                        if(i >= 5) break
                        addNationalNewsData(allNationalNewsList[i].mNationalNewsData)
                    }
                }

                override fun onFailure(call: Call<MainNationalNewsApiData>, t: Throwable) {
                    Log.d(TAG,"-------------------------------------------")
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 전국소식 생성일 순으로 정렬
    internal class AllNationalNewsList(mNationalNewsData: MainNationalNewsData, createAt: String) :
        Comparable<AllNationalNewsList> {

        val mNationalNewsData: MainNationalNewsData
        private val createAt: String

        override operator fun compareTo(nationalNews: AllNationalNewsList): Int {
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