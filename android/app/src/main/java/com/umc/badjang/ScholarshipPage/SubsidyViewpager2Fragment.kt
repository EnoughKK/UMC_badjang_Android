package com.umc.badjang.ScholarshipPage

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.MainActivity
import com.umc.badjang.Model.GetSupportDTO
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.databinding.FragmentSubsidyViewpager2Binding
import com.umc.badjang.databinding.RvScholarshipBinding
import com.umc.badjang.databinding.RvSubsidyBinding
import com.umc.badjang.utils.RESPONSE_STATE

class SubsidyViewpager2Fragment:Fragment() {
    private lateinit var viewBinding: FragmentSubsidyViewpager2Binding

    var activity: MainActivity? = null

    // 지원금 recyclerview adapter
    private var supportDatas = ArrayList<GetSupportDTO>()
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

        var category: String = ""
        var filter: String = ""
        var order: String = "desc"

        // 데이터 가져오기 (api 셋팅)
        loadData(category, filter, order)

        // 카테고리 선택
        viewBinding.spinnerCategory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_category_support, R.layout.spinner_layout)
        // 카테고리 클릭리스너
        viewBinding.spinnerCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                when (viewBinding.spinnerCategory.getItemAtPosition(position)) {
                    "전체" -> {
                        category = "전체"
                    }
                    "취업지원" -> {
                        category = "취업지원"
                    }
                    "창업지원" -> {
                        category = "창업지원"
                    }
                    "주거·금융" -> {
                        category = "주거·금융"
                    }
                    "생활·복지" -> {
                        category = "생활·복지"
                    }
                    "정책참여" -> {
                        category = "정책참여"
                    }
                    "코로나19" -> {
                        category = "코로나19"
                    }
                    else -> {
                        Log.d(ContentValues.TAG, "onItemSelected: null")
                    }
                }

                loadData(category,filter,order)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        // 정렬방식 선택
        viewBinding.spinnerSort.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_sort, R.layout.spinner_layout)
        // 정렬 클릭리스너
        viewBinding.spinnerSort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                when (viewBinding.spinnerSort.getItemAtPosition(position)) {
                    "인기순" -> {
                        filter = "인기순"
                    }
                    "날짜순" -> {
                        filter = "날짜순"
                    }
                    "댓글순" -> {
                        filter = "댓글순"
                    }
                }
                loadData(category,filter,order)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        return viewBinding.root
    }

    // 데이터 가져오기 (api 셋팅)
    private fun loadData(category: String, filter: String, order: String) {
        RetrofitManager.instance.searchSupport(category = category, filter = "", order = "", completion = {
                responseState, responseDataArrayList ->

            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d(ContentValues.TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
                    supportDatas = ArrayList<GetSupportDTO>(responseDataArrayList)

                    // recyclerview 셋팅
                    initRecycler()
                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(requireContext(), "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "api 호출 실패 : $responseDataArrayList")
                }
            }

        })
    }

    // recyclerview 셋팅
    private fun initRecycler() {

        // 지원금 recyclerview 셋팅
        subsidyAdapter = SubsidyRVAdapter(requireContext())
        viewBinding.subsidyRvContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.subsidyRvContainer.adapter = subsidyAdapter
        subsidyAdapter.datas = supportDatas

        // 클릭 리스너 셋팅
        subsidyAdapter.setItemClickListener(object: SubsidyRVAdapter.OnClickInterface{
            override fun onClick(view: View, position: Int, viewBinding: RvSubsidyBinding) {

                val support_idx = supportDatas[position].support_idx

                RetrofitManager.instance.searchSupportIDx(supportIdx = support_idx, completion = {
                        responseState ->

                    when(responseState) {
                        RESPONSE_STATE.OKAY -> {
                            Log.d(ContentValues.TAG, "지원금 뷰 api 호출 성공 : ${support_idx}")

                        }
                        RESPONSE_STATE.FAIL -> {
                            Toast.makeText(requireContext(), "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                            Log.d(ContentValues.TAG, "api 호출 실패 : ")
                        }
                    }

                })

                setFragmentResult("requestKey", bundleOf("bundleKey" to support_idx))

                // 지원금 디테일 페이지로 전환
                activity?.changeFragment(ScholarshipDetailFragment())
            }
        })
    }

}