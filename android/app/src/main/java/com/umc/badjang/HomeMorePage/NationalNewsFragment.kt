package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentNationalNewsBinding

// 홈화면 > 전국소식
class NationalNewsFragment : Fragment() {
    private lateinit var viewBinding: FragmentNationalNewsBinding // viewBinding

    // 전국소식 리스트 recyclerview adapter
    private val nationalNewsDatas = mutableListOf<NationalNewsData>()
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

        // recyclerview 세팅
        initRecycler()

        // 전국소식 데이터 추가
        val nationalNewsImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in 0..3) {
            addNationalNewsData(
                NationalNewsData("OO대학교",
                    nationalNewsImg, "자기추천장학금", "대상\n- 대학에 재학 중 또는 입,복학예정인 혼인 중이 아닌 무주택자",
                    65, 215))
        }

        // 이전 버튼 선택 시
        viewBinding.nationalNewsBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    // 전국소식 recyclerview 세팅
    private fun initRecycler() {
        nationalNewsAdapter = NationalNewsAdapter(requireContext())
        viewBinding.nationalNewsRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.nationalNewsRecyclerview.adapter = nationalNewsAdapter
        nationalNewsAdapter.datas = nationalNewsDatas
    }

    // 전국소식 데이터 추가
    private fun addNationalNewsData(nationalNews: NationalNewsData) {
        nationalNewsDatas.apply {
            add(nationalNews)
        }
        nationalNewsAdapter.notifyDataSetChanged()
    }
}