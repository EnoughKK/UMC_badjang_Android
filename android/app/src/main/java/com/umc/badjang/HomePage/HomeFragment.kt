package com.umc.badjang.HomePage

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import com.umc.badjang.Bookmarks.BookmarksFragment
import com.umc.badjang.HomeMorePage.*
import com.umc.badjang.HomePagaApi.*
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentHomeBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    val mySchoolSampleData = mutableListOf(mutableListOf("자기추천장학금", 215), mutableListOf("삼금문화장학재단 장학금", 215),
        mutableListOf("서울희망 대학 장학금", 215), mutableListOf("대산농촌재단 장학금", 215))

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
    private val nationalNewsDatas = mutableListOf<MainNationalNewsData>()
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
        for(i: Int in 0..(mySchoolSampleData.size - 1)) {
            addMySchoolData(MainMySchoolData(i+1, mySchoolSampleData[i][0].toString(), 215))
        }

        // 인기글 데이터 추가
        //for(i: Int in 0..3) {
        //    addPopularData(MainPopularData(i+1, "자기추천장학금 신청방법", 65,215))
        //}

        // 전국 소식 데이터 추가
        val img: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.national_news_img1)
        for(i: Int in 0..3) {
            addNationalNewsData(MainNationalNewsData(img, "국가근로장학금"))
        }
    }

    // 추천 배너 슬라이드 이미지 변경하기
    fun setMainRecommendSlideImage(){
        if(mainRecommendCurrentPosition == 5) mainRecommendCurrentPosition = 0
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
        nationalNewsDatas.apply {
            add(mainNationalNewsPost)
        }
        mainNationalNewsAdapter.notifyDataSetChanged()
    }

    // 인기글 조회 api
    private fun apiMainPopular() {
        retrofit!!.create(MainPopularApiService::class.java).getMainPopular()
            .enqueue(object : Callback<MainPopularApiData> {
                override fun onResponse(call: Call<MainPopularApiData>, response: Response<MainPopularApiData>) {
                    //Log.d(TAG,"인기글 -------------------------------------------")
                    //Log.d(TAG, "onResponse: ${response.body().toString()}")

                    var allPopularData: MainPopularApiData = response.body()!!
                    var popularData: MutableList<MainPopularApiResult> = allPopularData.result
                    for(i:Int in (0..popularData.size - 1)) {
                        if(i >= 4) break
                        addPopularData(MainPopularData(i+1, popularData[i].post_content, 65,215))
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
        retrofit!!.create(MainNationalNewsApiService::class.java).getMainNationalNews()
            .enqueue(object : Callback<MainNationalNewsApiData> {
                override fun onResponse(call: Call<MainNationalNewsApiData>, response: Response<MainNationalNewsApiData>) {
                    Log.d(TAG,"전국소식 -------------------------------------------")
                    Log.d(TAG, "onResponse: ${response.body().toString()}")

                }

                override fun onFailure(call: Call<MainNationalNewsApiData>, t: Throwable) {
                    Log.d(TAG,"-------------------------------------------")
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }
}