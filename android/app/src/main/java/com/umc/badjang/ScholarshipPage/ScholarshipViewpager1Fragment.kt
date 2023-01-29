package com.umc.badjang.ScholarshipPage

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentScholarshipViewpager1Binding
import com.umc.badjang.retrofit.RetrofitManager
import com.umc.badjang.utils.RESPONSE_STATE

class ScholarshipViewpager1Fragment:Fragment() {
    private lateinit var viewBinding: FragmentScholarshipViewpager1Binding

    var activity: MainActivity? = null

    val scholarshipSampleData = mutableListOf(mutableListOf("부경대학교", "교내 재학생 장학금", "자기추천장학금", "부경대학교 장학금", true, 215, 64), mutableListOf("경상대학교", "교내 재학생 장학금", "삼금문화장학재단 장학금", "경상대학교 장학금", false, 215, 65),
        mutableListOf("한국해양대학교", "교내 재학생 장학금", "서울희망 대학 장학금", "한국해양대학교 장학금", false, 215, 66), mutableListOf("부산대학교", "교내 재학생 장학금", "대산농촌재단 장학금", "부산대학교 장학금", false, 215, 67), mutableListOf("부산대학교", "교내 재학생 장학금", "대산농촌재단 장학금", "부산대학교 장학금", false, 215, 67))

    // 장학금 recyclerview adapter
    private var scholarshipDatas = ArrayList<GetScholarshipDTO>()
    private lateinit var scholarshipAdapter: ScholarshipRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScholarshipViewpager1Binding.inflate(layoutInflater);

        // 데이터 가져오기 (api 셋팅)
        loadData()

        // 장학금 데이터 추가
//        for(i: Int in 0..(scholarshipSampleData.size - 1)) {
//            addscholarshipData(ScholarshipRVDTO(scholarshipSampleData[i][0].toString(), scholarshipSampleData[i][1].toString(), scholarshipSampleData[i][2].toString(),
//                scholarshipSampleData[i][3].toString(), scholarshipSampleData[i][4] as Boolean, scholarshipSampleData[i][5] as Int, scholarshipSampleData[i][6] as Int))
//        }

        // 카테고리 선택
        viewBinding.spinnerCategory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_category, R.layout.spinner_layout)

        // 정렬방식 선택
        viewBinding.spinnerSort.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_sort, R.layout.spinner_layout)

        return viewBinding.root
    }

    // 데이터 가져오기 (api 셋팅)
    private fun loadData() {
        RetrofitManager.instance.searchScholarship(category = null, filter = null, order = null, completion = {
                responseState, responseDataArrayList ->

            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d(TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
                    scholarshipDatas = ArrayList<GetScholarshipDTO>(responseDataArrayList)

                    // recyclerview 셋팅
                    initRecycler()
                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(requireContext(), "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "api 호출 실패 : $responseDataArrayList")
                }
            }

        })
    }

    // recyclerview 셋팅
    private fun initRecycler() {
        Log.d(TAG, "복사 성공 : ${scholarshipDatas?.size}")

        // 장학금 recyclerview 셋팅
        scholarshipAdapter = ScholarshipRVAdapter(requireContext())
        viewBinding.scholarshipRvContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.scholarshipRvContainer.adapter = scholarshipAdapter
        scholarshipAdapter.datas = scholarshipDatas

        // 클릭 리스너 셋팅
        scholarshipAdapter.setItemClickListener(object: ScholarshipRVAdapter.OnClickInterface{
            override fun onClick(view: View, position: Int) {

                // 장학금 디테일 페이지로 전환
                activity?.changeFragment(ScholarshipDetailFragment())
            }
        })
    }

}