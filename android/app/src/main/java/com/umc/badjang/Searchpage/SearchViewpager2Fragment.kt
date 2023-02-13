package com.umc.badjang.Searchpage

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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.MainActivity
import com.umc.badjang.Model.GetScholarshipDTO
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.ScholarshipDetailFragment
import com.umc.badjang.ScholarshipPage.ScholarshipRVAdapter
import com.umc.badjang.databinding.FragmentSearchViewpager2Binding
import com.umc.badjang.utils.RESPONSE_STATE

class SearchViewpager2Fragment: Fragment() {
    private lateinit var viewBinding: FragmentSearchViewpager2Binding

    var activity: MainActivity? = null

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
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentSearchViewpager2Binding.inflate(layoutInflater);
        return viewBinding.root
    }

    // 데이터 가져오기 (api 셋팅)
    private fun loadData(category: String, filter: String, order: String) {
        RetrofitManager.instance.searchScholarship(category = category, filter = filter, order = order, completion = {
                responseState, responseDataArrayList ->

            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d(ContentValues.TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
                    scholarshipDatas = ArrayList<GetScholarshipDTO>(responseDataArrayList)

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
        Log.d(ContentValues.TAG, "복사 성공 : ${scholarshipDatas?.size}")

        // 장학금 recyclerview 셋팅
        scholarshipAdapter = ScholarshipRVAdapter(requireContext())
        viewBinding.searchScholarshipViewpagerRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.searchScholarshipViewpagerRecyclerview.adapter = scholarshipAdapter
        scholarshipAdapter.datas = scholarshipDatas

        // 클릭 리스너 셋팅
        scholarshipAdapter.setItemClickListener(object: ScholarshipRVAdapter.OnClickInterface{
            override fun onClick(view: View, position: Int) {

                val scholarshipIdx: Long = scholarshipDatas[position].scholarship_idx!!

                // 장학금 디테일 페이지로 전환
                activity?.SendDataFragment(ScholarshipDetailFragment(), scholarshipIdx)
            }
        })
    }

}