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
import com.umc.badjang.databinding.FragmentMySchoolBinding

// 홈화면 > 우리학교 장학금
class MySchoolFragment : Fragment() {
    private lateinit var viewBinding: FragmentMySchoolBinding // viewBinding

    // 우리학교 장학금 recyclerview adapter
    private val mySchoolDatas = mutableListOf<NationalNewsData>()
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

        // 이전 버튼 선택 시
        viewBinding.mySchoolBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        // recyclerview 세팅
        initRecycler()

        // 우리학교 데이터 추가
        val mySchoolImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in 0..3) {
            addMySchoolData(
                NationalNewsData("OO대학교",
                    mySchoolImg, "자기추천장학금", "대상\n- 대학에 재학 중 또는 입,복학예정인 혼인 중이 아닌 무주택자",
                    65, 215))
        }
    }

    // 우리학교 recyclerview 세팅
    private fun initRecycler() {
        mySchoolAdapter = NationalNewsAdapter(requireContext())
        viewBinding.mySchoolRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.mySchoolRecyclerview.adapter = mySchoolAdapter
        mySchoolAdapter.datas = mySchoolDatas
    }

    // 우리학교 데이터 추가
    private fun addMySchoolData(mySchoolData: NationalNewsData) {
        mySchoolDatas.apply {
            add(mySchoolData)
        }
        mySchoolAdapter.notifyDataSetChanged()
    }

}