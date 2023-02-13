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
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.Model.GetScholarshipDTO
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.ScholarshipDetailFragment
import com.umc.badjang.ScholarshipPage.ScholarshipRVAdapter
import com.umc.badjang.Searchpage.models.ScholarshipSearchResponse
import com.umc.badjang.databinding.FragmentSearchViewpager2Binding
import com.umc.badjang.utils.Constants
import com.umc.badjang.utils.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class SearchViewpager2Fragment: Fragment() {
    private lateinit var viewBinding: FragmentSearchViewpager2Binding

    var activity: MainActivity? = null

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)
    val query_text = ApplicationClass.cSharedPreferences.getString(ApplicationClass.QUREY_TEXT,null)

    val searchRetrofit= ApplicationClass.sRetrofit.create(SearchRetrofit::class.java)

    // 장학금 recyclerview adapter
    private var scholarshipDatas = ArrayList<GetScholarshipDTO>()
    private lateinit var scholarshipAdapter: Search2RVAdapter

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
        loadScholarship()
        return viewBinding.root
    }

    // 데이터 가져오기 (api 셋팅)
    private fun loadScholarship() {
        searchRetrofit.scholarshipsearch(query_text.toString()).enqueue(object :Callback<ScholarshipSearchResponse>{
            override fun onResponse(
                call: Call<ScholarshipSearchResponse>,
                response: Response<ScholarshipSearchResponse>
            ) {
                Log.d(ContentValues.TAG,"장학금검색 api -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                when(response.body()!!.code) {
                    1000->{
                        initRecycler()

                    }
                    else->{
                        Log.e("장학금검색 실패","${response.message()}")
                        Toast.makeText(ApplicationClass.instance,"${response.message()}",Toast.LENGTH_SHORT).show()
                    }
                }

            }

            override fun onFailure(call: Call<ScholarshipSearchResponse>, t: Throwable) {
                Log.d(ContentValues.TAG,"장학금 검색 api -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })


    }

    // recyclerview 셋팅
    private fun initRecycler() {
        Log.d(ContentValues.TAG, "복사 성공 : ${scholarshipDatas?.size}")

        // 장학금 recyclerview 셋팅
        scholarshipAdapter = Search2RVAdapter(requireContext())
        viewBinding.searchScholarshipViewpagerRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.searchScholarshipViewpagerRecyclerview.adapter = scholarshipAdapter
        scholarshipAdapter.datas = scholarshipDatas

        // 클릭 리스너 셋팅
        scholarshipAdapter.setItemClickListener(object: Search2RVAdapter.OnClickInterface{
            override fun onClick(view: View, position: Int) {

                val scholarshipIdx: Long = scholarshipDatas[position].scholarship_idx!!

                // 장학금 디테일 페이지로 전환
                activity?.SendDataFragment(ScholarshipDetailFragment(), scholarshipIdx)
            }
        })
    }

}