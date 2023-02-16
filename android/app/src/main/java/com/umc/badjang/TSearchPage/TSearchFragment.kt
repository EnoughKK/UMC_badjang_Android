package com.umc.badjang.TSearchPage

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.ScholarshipDetailFragment
import com.umc.badjang.Searchpage.*
import com.umc.badjang.Searchpage.SearchHistroy.ISearchHistroyRecyclerView
import com.umc.badjang.Searchpage.SearchHistroy.SearchHistoryRecyclerViewAdapter
import com.umc.badjang.Searchpage.SearchHistroy.SharedPrefManager
import com.umc.badjang.Searchpage.models.DeleteSearchResponse
import com.umc.badjang.Searchpage.models.RecentSearchResponse
import com.umc.badjang.Searchpage.models.ScholarshipData
import com.umc.badjang.Searchpage.models.SearchData
import com.umc.badjang.databinding.FragmentTsearchBinding
import com.umc.badjang.utils.Constants
import com.umc.badjang.utils.RESPONSE_STATE
import com.umc.badjang.utils.toSimpleString
import kotlinx.android.synthetic.main.fragment_search_lookup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class TSearchFragment : Fragment(),SearchView.OnQueryTextListener, ISearchHistroyRecyclerView, View.OnClickListener {


    private var scholarshipDatas = ArrayList<ScholarshipData>()
    private lateinit var scholarshipAdapter: ScholarRecyeclerViewAdapter

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

    val text = ApplicationClass.cSharedPreferences.getString(ApplicationClass.QUREY_TEXT,null)

   private lateinit var viewBinding : FragmentTsearchBinding

    val searchRetrofit = ApplicationClass.sRetrofit.create(SearchRetrofit::class.java)

    //검색 기록 배열
    private var searchHistroyList = ArrayList<SearchData>()
    private lateinit var mySearchHistoryRecyclerViewAdapter: SearchHistoryRecyclerViewAdapter

    //서치뷰
    private lateinit var mySearchView : SearchView

    //서치뷰 에딧 텍스트
    private lateinit var mySearchViewEditText : EditText

    //프래그먼트 전환을 위해
    var activity : MainActivity? = null

    override fun onAttach(context:Context){
        super.onAttach(context)
        activity=getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        viewBinding=FragmentTsearchBinding.inflate(layoutInflater)

        viewBinding.searchMainbar.SearchUpBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }


        // 액티비티에서 어떤 액션바를 사용할지 설정한다. // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.search_main_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar) //커스텀한 toolbar를 액션바로 사용
        //setHasOptionsMenu(true)


        //저장된 검색 기록 가져오기
        this.searchHistroyList = SharedPrefManager.getSearchHistoryList() as ArrayList<SearchData>

       //handleSearchViewUI()

        this.searchHistroyList.forEach {
            Log.d("검색기록", "저장된 검색 기록 - it.term : ${it.term} , it.timestamp: ${it.timestamp}")
        }


        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //검색 리사이클러뷰 준비
        searchHistoryRecyclerViewSetting(searchHistroyList)

        //initViewPager()
    }


/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_top_bar,menu)
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(Constants.TAG, "onCreateOptionMenu")

        this.mySearchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력하세요."
            this.setOnQueryTextFocusChangeListener { view, hasExpanded ->
                when (hasExpanded) {
                    true -> {
                        Log.d(Constants.TAG, "서치뷰 열림")
                        handleSearchViewUI()
                    }
                    false -> {
                        Log.d(Constants.TAG, "서치뷰 닫힘")
                        search_history_recycler_view_label.visibility=View.INVISIBLE
                        search_history_recycler_view.visibility = View.INVISIBLE
                    }
                }
                this.setOnQueryTextListener(this@TSearchFragment)
                mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
                mySearchViewEditText.apply {
                    this.filters = arrayOf(InputFilter.LengthFilter(50))
                    this.setTextColor(Color.BLACK)
                    this.setHintTextColor(Color.GRAY)
                }
            }
        }
    }*/


    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("검색입력","onQueryTextSubmit() : $query")

        if (!query.isNullOrEmpty()){

            this.insertSearchTermHistory(query)
            this.searchAPICall(query)
        }
        this.mySearchView.setQuery("",false)
        this.mySearchView.clearFocus()
        //this.viewBinding.searchMainbar.collapseActionView()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("검색입력","onQueryTextChange() : $newText")

        val userInputText = newText.let{
            it
        }?: ""

        if(userInputText.count()==50){
            Toast.makeText(ApplicationClass.instance,"검색어는 50자까지 입력 가능합니다!", Toast.LENGTH_SHORT).show()
        }

        if(userInputText.length in 1..50){
            getScholar(userInputText)
        }
        return true
    }
    //검색 아이템삭제 버튼 이벤트
    override fun onSearchItemDeleteClicked(position: Int) {
        Log.d(
            Constants.TAG,
            "SearchLookupFragment - onSearchItemDeleteClicked() called / position : $position"
        )
        //해당 번째의 검색어를 삭제하고 다시 저장
        //해당 요소 삭제

        this.searchHistroyList.removeAt(position)
        //데이터 덮어쓰기
        SharedPrefManager.storeSearchHistoryList(this.searchHistroyList)
        //데이터 변경 된 것을 알려줌
        this.mySearchHistoryRecyclerViewAdapter.notifyDataSetChanged()

        searchDeleteApiCall()
    }

    //검색 아이템 버튼 이벤트
    override fun onSearchItemClicked(position: Int) {
        Log.d(Constants.TAG,"SearchLookupFragment - onSearchItemClicked() called / position : $position")
        //해당 검색어의 api 호출
        val queryString : String = this.searchHistroyList[position].term

        searchAPICall(queryString)
        getScholar(queryString)
        this.insertSearchTermHistory(searchTerm = queryString)
        search_history_recycler_view.visibility=View.INVISIBLE
        search_history_recycler_view_label.visibility=View.INVISIBLE
        //this.topAppBar.collapseActionView()
    }

    //검색 기록 리사이클러뷰 준비
    private fun searchHistoryRecyclerViewSetting(searchHistoryList:ArrayList<SearchData>){
        Log.d(Constants.TAG,"검색 프레그먼트 - searchHistoryRecyclerViewSetting() called")

        this.mySearchHistoryRecyclerViewAdapter = SearchHistoryRecyclerViewAdapter(this)
        this.mySearchHistoryRecyclerViewAdapter.submitList(searchHistoryList)

        //리니어 레이아웃으로 가져오기
        val myLinearLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        myLinearLayoutManager.stackFromEnd=true //최근 검색어가 가장 위에 쌓임

        search_history_recycler_view.apply{
            layoutManager = myLinearLayoutManager
            this.scrollToPosition(mySearchHistoryRecyclerViewAdapter.itemCount - 1)
            adapter = mySearchHistoryRecyclerViewAdapter
        }
    }


    // 검색 api 호출
    private fun searchAPICall(query: String?) {
        searchRetrofit.recentsearch(jwt.toString()).enqueue(object :
            Callback<RecentSearchResponse> {
            override fun onResponse(call: Call<RecentSearchResponse>, response: Response<RecentSearchResponse>) {
                Log.d(ContentValues.TAG,"검색 api -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                when(response.body()!!.code) {
                    1000->{
                        val received = response.body()
                        searchHistoryIdx =received?.result?.search_history_idx!!.toLong()
                        val text : String = query.toString()
                        Log.e(Constants.TAG,"${text.toString()}")

                        //query 저장
                        ApplicationClass.cSharedPreferences.edit().putString("QUERY-TEXT",text).commit()
                        search_history_recycler_view.visibility = View.INVISIBLE
                        search_history_recycler_view_label.visibility = View.INVISIBLE

                    }
                    else->{
                        Log.e("검색 실패","${response.message()}")
                        Toast.makeText(ApplicationClass.instance,"${response.message()}",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<RecentSearchResponse>, t: Throwable) {
                Log.d(ContentValues.TAG,"검색 api -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }


    //검색 삭제 api
    private fun searchDeleteApiCall(){
        searchRetrofit.deletesearch(jwt.toString(), searchHistoryIdx).enqueue(object :
            Callback<DeleteSearchResponse> {
            override fun onResponse(
                call: Call<DeleteSearchResponse>,
                response: Response<DeleteSearchResponse>
            ) {
                Log.d(ContentValues.TAG,"검색삭제 api -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                when(response.body()!!.code) {
                    1000->{
                        Toast.makeText(ApplicationClass.instance,"${response.message()}",Toast.LENGTH_SHORT).show()
                    }
                    else->{
                        Log.e("검색삭제 실패","${response.message()}")
                        Toast.makeText(ApplicationClass.instance,"${response.message()}",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<DeleteSearchResponse>, t: Throwable) {
                Log.d(ContentValues.TAG,"검색삭제 api -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun handleSearchViewUI(){
        Log.d(Constants.TAG,"handleSearchViewUI() called / size : ${this.searchHistroyList.size}")
        if(this.searchHistroyList.size>0){
            search_history_recycler_view.visibility = View.VISIBLE
            search_history_recycler_view_label.visibility = View.VISIBLE
        }else{
            search_history_recycler_view.visibility = View.INVISIBLE
            search_history_recycler_view_label.visibility = View.INVISIBLE
        }
    }

    // 검색어 저장
    private fun insertSearchTermHistory(searchTerm: String){
        Log.d(Constants.TAG, "검색 - insertSearchTermHistory() called")

        if(SharedPrefManager.checkSearchHistoryMode() == true){
            // 중복 아이템 삭제
            var indexListToRemove = ArrayList<Int>()

            this.searchHistroyList.forEachIndexed{ index, searchDataItem ->

                if(searchDataItem.term == searchTerm){
                    Log.d(Constants.TAG, "index: $index")
                    indexListToRemove.add(index)
                }
            }

            indexListToRemove.forEach {
                this.searchHistroyList.removeAt(it)
            }

            // 새 아이템 넣기
            val newSearchData = SearchData(term = searchTerm, timestamp = Date().toSimpleString())
            this.searchHistroyList.add(newSearchData)

            // 기존 데이터에 덮어쓰기
            SharedPrefManager.storeSearchHistoryList(this.searchHistroyList)

            this.mySearchHistoryRecyclerViewAdapter.notifyDataSetChanged()
        }

    }



    //장학금 호출
    private fun getScholar (query: String?){
        RetrofitManager.instance.scholarshipsearch(query =text.toString(), completion = {responseState: RESPONSE_STATE, arrayList: ArrayList<ScholarshipData>? ->
            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d(ContentValues.TAG, "api 호출 성공 : ${arrayList?.size}")
                    scholarshipDatas = ArrayList<ScholarshipData>(arrayList)

                    // recyclerview 셋팅
                    initRecycler()
                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(requireContext(), "api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "api 호출 실패 : $arrayList")
                }
                RESPONSE_STATE.NO_CONTENT -> {
                }
            }
        })
    }


    // recyclerview 셋팅
    private fun initRecycler() {
        Log.d(ContentValues.TAG, "복사 성공 : ${scholarshipDatas?.size}")

        // 장학금 recyclerview 셋팅
        scholarshipAdapter = ScholarRecyeclerViewAdapter(requireContext())
        viewBinding.tsearchScholarshipRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.tsearchScholarshipRecyclerView.adapter = scholarshipAdapter
        scholarshipAdapter.datas = scholarshipDatas

        // 클릭 리스너 셋팅
        scholarshipAdapter.setItemClickListener(object: ScholarRecyeclerViewAdapter.OnClickInterface{
            override fun onClick(view: View, position: Int) {

                val scholarshipIdx: Long = scholarshipDatas[position].scholarship_idx!!

                // 장학금 디테일 페이지로 전환
                activity?.SendDataFragment(ScholarshipDetailFragment(), scholarshipIdx,"")
            }
        })
    }

    override fun onClick(view: View?) {

    }

}