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
import com.google.gson.JsonElement
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity

import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.Model.GetScholarshipDTO
import com.umc.badjang.ScholarshipPage.ScholarshipDetailFragment
import com.umc.badjang.ScholarshipPage.ScholarshipRVAdapter
import com.umc.badjang.Searchpage.models.ScholarshipSearchResponse
import com.umc.badjang.databinding.FragmentMoreScholarshipBinding
import com.umc.badjang.databinding.FragmentScholarshipViewpager1Binding
import com.umc.badjang.databinding.FragmentSearchViewpager2Binding
import com.umc.badjang.utils.Constants
import com.umc.badjang.utils.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class SearchViewpager2Fragment: Fragment() {
    private lateinit var viewBinding: FragmentScholarshipViewpager1Binding

    private var mScholarshipDatas = ArrayList<GetScholarshipDTO>()

    var activity: MainActivity? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

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
        viewBinding =FragmentScholarshipViewpager1Binding.inflate(layoutInflater);

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        var category: String = ""
        var filter: String = ""
        var order: String = "desc"

        // 데이터 가져오기 (api 셋팅)
        loadData(category,filter,order)

        // 카테고리 선택
        viewBinding.spinnerCategory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_category, R.layout.spinner_layout)
        // 카테고리 클릭리스너
        viewBinding.spinnerCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                when (viewBinding.spinnerCategory.getItemAtPosition(position)) {
                    "전체" -> {
                        category = "전체"
                    }
                    "국가장학" -> {
                        category = "국가장학"
                    }
                    "KRA와 함께하는 농어촌 희망재단 장학금" -> {
                        category = "KRA와 함께하는 농어촌 희망재단 장학금"
                    }
                    "교내 신입생 입학성적 우수장학금" -> {
                        category = "교내 신입생 입학성적 우수장학금"
                    }
                    "교내 재학생 장학금" -> {
                        category = "교내 재학생 장학금"
                    }
                    "교외장학" -> {
                        category = "교외장학"
                    }
                    "교내장학" -> {
                        category = "교내장학"
                    }
                    "학비대출" -> {
                        category = "학비대출"
                    }
                    "국가근로" -> {
                        category = "국가근로"
                    }
                    "성적우수장학금" -> {
                        category = "성적우수장학금"
                    }
                    "특별감면장학금" -> {
                        category = "특별감면장학금"
                    }
                    "가계곤란자 장학금" -> {
                        category = "가계곤란자 장학금"
                    }
                    "근로 장학금" -> {
                        category = "근로 장학금"
                    }
                    "기타" -> {
                        category = "기타"
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
        RetrofitManager.instance.searchScholarship(category = category, filter = filter, order = order, completion = {
                responseState, responseDataArrayList ->

            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d(ContentValues.TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
                    mScholarshipDatas = ArrayList<GetScholarshipDTO>(responseDataArrayList)


                    // 즐겨찾기 유무 조회
//                    for(i: Int in (0..mScholarshipDatas.size)) {
//                        checkScholarshipBookmark(i)
//                    }

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
    public fun initRecycler() {
        Log.d(ContentValues.TAG, "복사 성공 : ${scholarshipDatas?.size}")

        // 장학금 recyclerview 셋팅
        scholarshipAdapter = ScholarshipRVAdapter(requireContext())
        viewBinding.scholarshipRvContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.scholarshipRvContainer.adapter = scholarshipAdapter
        scholarshipAdapter.datas = mScholarshipDatas

        // 클릭 리스너 셋팅
        scholarshipAdapter.setItemClickListener(object: ScholarshipRVAdapter.OnClickInterface{
            override fun onClick(view: View, position: Int) {

                val scholarshipIdx: Long = mScholarshipDatas[position].scholarship_idx!!

                // 장학금 디테일 페이지로 전환
                activity?.SendDataFragment(ScholarshipDetailFragment(), scholarshipIdx, "")
            }
        })
    }

    // 즐겨찾기 유무 조회 API
//    private fun checkScholarshipBookmark(i: Int) {
//        RetrofitManager.instance.scholarshipBookmark(jwt!!, mScholarshipDatas[i].scholarship_idx!!.toInt(), completion = {
//                responseState, responseDataArrayList ->
//
//            when(responseState){
//                RESPONSE_STATE.OKAY -> {
//                    Log.d(ContentValues.TAG, "api 호출 성공 : ${responseDataArrayList?.size}")
//
//                    val scholarshipBookmarkDatas = ArrayList<ScholarshipBookmarkDTO>(responseDataArrayList)
//                    mScholarshipDatas[i].isBookmarked = scholarshipBookmarkDatas[0].bookmark_check == "Y"
//
//                }
//                RESPONSE_STATE.FAIL -> {
//                    Toast.makeText(context, "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
//                    Log.d(ContentValues.TAG, "api 호출 실패 : $responseDataArrayList")
//                }
//
//            }
//
//        })
//    }


}