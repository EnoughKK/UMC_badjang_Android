package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentSubsidyViewpager2Binding

class SubsidyViewpager2Fragment:Fragment() {
    private lateinit var viewBinding: FragmentSubsidyViewpager2Binding

    var activity: MainActivity? = null

    val subsidySampleData = mutableListOf(mutableListOf("부경대학교", "교내 재학생 지원금", "자기추천지원금", "부경대학교 지원금", true, 215, 64), mutableListOf("경상대학교", "교내 재학생 지원금", "삼금문화장학재단 지원금", "경상대학교 지원금", false, 215, 65),
        mutableListOf("한국해양대학교", "교내 재학생 지원금", "서울희망 대학 지원금", "한국해양대학교 지원금", false, 215, 66), mutableListOf("부산대학교", "교내 재학생 지원금", "대산농촌재단 지원금", "부산대학교 지원금", false, 215, 67))

    // 지원금 recyclerview adapter
    private val subsidyDatas = mutableListOf<SubsidyRVDTO>()
    private lateinit var subsidyAdapter: SubsidyRVAdapter

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
        viewBinding = FragmentSubsidyViewpager2Binding.inflate(layoutInflater);

        // recyclerview 셋팅
        initRecycler()

        // 지원금 데이터 추가
        for(i: Int in 0..(subsidySampleData.size - 1)) {
            addSubsidyData(SubsidyRVDTO(subsidySampleData[i][0].toString(), subsidySampleData[i][1].toString(), subsidySampleData[i][2].toString(),
                subsidySampleData[i][3].toString(), subsidySampleData[i][4] as Boolean, subsidySampleData[i][5] as Int, subsidySampleData[i][6] as Int))
        }

        // 카테고리 선택
        viewBinding.spinnerCategory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_category, R.layout.spinner_layout)

        // 정렬방식 선택
        viewBinding.spinnerSort.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_sort, R.layout.spinner_layout)

        return viewBinding.root
    }

    // recyclerview 셋팅
    private fun initRecycler() {

        // 장학금 recyclerview 셋팅
        subsidyAdapter = SubsidyRVAdapter(requireContext())
        viewBinding.subsidyRvContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.subsidyRvContainer.adapter = subsidyAdapter
        subsidyAdapter.datas = subsidyDatas
    }

    // 지원금 데이터 추가
    private fun addSubsidyData(subsidyPost: SubsidyRVDTO) {
        subsidyDatas.apply {
            add(subsidyPost)
        }
        subsidyAdapter.notifyDataSetChanged()
    }
}