package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.HomePage.MainMySchoolAdapter
import com.umc.badjang.HomePage.MainMySchoolData
import com.umc.badjang.HomePage.MainPopularData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentPopularPostBinding

// 홈화면 > 인기글
class PopularPostFragment : Fragment() {
    private lateinit var viewBinding: FragmentPopularPostBinding // viewBinding

    // 인기글 리스트 recyclerview adapter
    private val popularPostDatas = mutableListOf<PopularPostData>()
    private lateinit var popularPostAdapter: PopularPostAdapter

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

        // 인기글 데이터 추가
        val profileImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.non_profile)
        val popularPostImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in 0..3) {
            addPopularPostData(
                PopularPostData(profileImg, "익명", "2022.12.28",
                "자기추천 장학금 신청방법", "자기추천 장학금 신청방법은 성적향상도(15), 진로탐색경험 (15),  대외활동(10), 자격층 취득(5), 지도교수상담(5) 총 50점 중 ...",
                popularPostImg, 65, 215, 65))
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

}