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
import com.umc.badjang.HomePagaApi.MainApiClient
import com.umc.badjang.HomePagaApi.MainPopularApiData
import com.umc.badjang.HomePagaApi.MainPopularApiResult
import com.umc.badjang.HomePagaApi.MainPopularApiService
import com.umc.badjang.HomePage.MainMySchoolAdapter
import com.umc.badjang.HomePage.MainMySchoolData
import com.umc.badjang.HomePage.MainPopularData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentPopularPostBinding
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

        // recyclerview 세팅
        initRecycler()

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 인기글 조회 api
        apiMainPopular()

        // 임시 데이터
        profileImg = BitmapFactory.decodeResource(resources, R.drawable.non_profile)

        val sampleData = mutableListOf<PopularPostData>(PopularPostData(profileImg!!, "익명", "2022.12.28",
            "이번에 2월에 졸업하는 사람도 해당되나요?", "다가오는 2월에 졸업하는 사람도 장학금을 받을 수 있는지 궁금합니다!\n알려주세요 ㅠㅠ",
            null, 0, 1, 0),
            PopularPostData(profileImg!!, "익명", "2022.12.28",
                "혹시 게시판을 만들 수는 없나요?", "게시판을 따로 만들어서 원하는 정보만 공유하고 싶은데, 게시판을 만들 수 있나요?",
                null, 0, 8, 1),
            PopularPostData(profileImg!!, "익명", "2022.12.28",
                "몇 학점 이상 들어야하는 조건이 있나요?", "경상대 학생입니다!!\n개척장학금을 받으려면 몇 학점 이상을 수강해야 한다는 조건이 있나요?",
                null, 0, 5, 2))

        // 인기글 데이터 추가
        profileImg = BitmapFactory.decodeResource(resources, R.drawable.non_profile)
        val popularPostImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in 0..(sampleData.size - 1)) {
            addPopularPostData(
                sampleData[i])
        }

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
                    //Log.d(TAG,"인기글 -------------------------------------------")
                    //Log.d(TAG, "onResponse: ${response.body().toString()}")

                    var allPopularData: MainPopularApiData = response.body()!!
                    var popularData: MutableList<MainPopularApiResult> = allPopularData.result
                    /*for(i:Int in (0..popularData.size - 1)) {
                        addPopularPostData(
                            PopularPostData(profileImg!!, "익명", "2022.12.28",
                                popularData[i].post_content, "자기추천 장학금 신청방법은 성적향상도(15), 진로탐색경험 (15),  대외활동(10), 자격층 취득(5), 지도교수상담(5) 총 50점 중 ...",
                                popularPostImg, 65, 215, 65))
                    }*/

                }

                override fun onFailure(call: Call<MainPopularApiData>, t: Throwable) {
                    //Log.d(TAG,"-------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }
}