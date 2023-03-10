package com.umc.badjang.Searchpage

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Searchpage.SearchHistroy.ISearchHistroyRecyclerView
import com.umc.badjang.Searchpage.SearchHistroy.SearchHistoryRecyclerViewAdapter
import com.umc.badjang.Searchpage.SearchHistroy.SharedPrefManager
import com.umc.badjang.Searchpage.models.DeleteSearchResponse
import com.umc.badjang.Searchpage.models.RecentSearchResponse
import com.umc.badjang.Searchpage.models.SearchData
import com.umc.badjang.databinding.FragmentSearchLookupBinding
import com.umc.badjang.utils.Constants.TAG
import com.umc.badjang.utils.toSimpleString
import kotlinx.android.synthetic.main.fragment_search_lookup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


var searchHistoryIdx:Long = 0

var query_string : String = "0"

class SearchLookupFragment : Fragment(), ISearchHistroyRecyclerView {
    private lateinit var viewBinding: FragmentSearchLookupBinding// viewBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    //어댑터
    private lateinit var  fragmentStateAdapter: SFragmentStateAdapter
    private lateinit var  mySearchHistoryRecyclerViewAdapter: SearchHistoryRecyclerViewAdapter


    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

    val searchRetrofit= ApplicationClass.sRetrofit.create(SearchRetrofit::class.java)


    // 검색 기록 배열
    private var searchHistoryList = ArrayList<SearchData>()

    //서치뷰
    private lateinit var mySearchView: SearchView

    //서치뷰 에딧 텍스트
    private lateinit var mySearchViewEditText: EditText

    // 프래그먼트 전환을 위해
    var activity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity=getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSearchLookupBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val search_toolbar: Toolbar = requireActivity().findViewById(R.id.search_main_bar)
        (activity as AppCompatActivity).setSupportActionBar(search_toolbar)

       //검색창에 입력했을 때
        viewBinding.searchMainBar.searchWord.setOnEditorActionListener{v,actionId, event ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                query_string = viewBinding.searchMainBar.searchWord.text.toString()

                if (query_string!=""){

                    if(query_string.count()==50){
                        Toast.makeText(ApplicationClass.instance,"검색어는 50자까지 입력 가능합니다!",Toast.LENGTH_SHORT).show()
                        viewBinding.searchMainBar.searchWord.setText("")
                    }

                    this.insertSearchTermHistory(query_string)
                    this.searchAPICall(query_string)
                    viewBinding.searchMainBar.searchWord.setText("")
                }

                handled = true
            }
            handled
        }

        // 저장된 검색 기록 가져오기
        this.searchHistoryList = SharedPrefManager.getSearchHistoryList() as ArrayList<SearchData>

        this.searchHistoryList.forEach {
            Log.d("검색기록", "저장된 검색 기록 - it.term : ${it.term} , it.timestamp: ${it.timestamp}")
        }
        handleSearchViewUI()

        // 검색 리사이클러뷰 준비
        this.searchHistoryRecyclerViewSetting(this.searchHistoryList)

        viewBinding.searchMainBar.searchWord.setOnEditorActionListener{v,actionId, event ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                query_string = viewBinding.searchMainBar.searchWord.text.toString()
                handled = true
            }
            handled
        }


        initViewPager()
    }

    //검색 기록 리사이클러뷰 준비
    private fun searchHistoryRecyclerViewSetting(searchHistoryList: ArrayList<SearchData>){
        Log.d(TAG,"검색 프레그먼트 - searchHistoryRecyclerViewSetting() called")

        this.mySearchHistoryRecyclerViewAdapter = SearchHistoryRecyclerViewAdapter(this)
        this.mySearchHistoryRecyclerViewAdapter.submitList(searchHistoryList)

        //리니어 레이아웃으로 가져오기
        val myLinearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,true)
        myLinearLayoutManager.stackFromEnd=true //최근 검색어가 가장 위에 쌓임

        search_history_recycler_view.apply{
            layoutManager = myLinearLayoutManager
            this.scrollToPosition(mySearchHistoryRecyclerViewAdapter.itemCount - 1)
            adapter = mySearchHistoryRecyclerViewAdapter
        }
    }


    //검색했을 때 나오는 viewpager 초기화
    fun initViewPager() {

        viewPager = viewBinding.searchViewPager
        fragmentStateAdapter = SFragmentStateAdapter(requireActivity())
        viewPager.adapter = fragmentStateAdapter

        tabLayout = viewBinding.searchTabLayout
        viewPager.setUserInputEnabled(false)

        // 탭 레이아웃 연결
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "전체"
                1 -> tab.text = "장학금"
                2 -> tab.text = "지원금"
                3 -> tab.text = "게시글"
            }
        }.attach()
    }



    //검색 아이템삭제 버튼 이벤트
    override fun onSearchItemDeleteClicked(position: Int) {
        Log.d(
            TAG,
            "SearchLookupFragment - onSearchItemDeleteClicked() called / position : $position"
        )
        //해당 번째의 검색어를 삭제하고 다시 저장
        //해당 요소 삭제

        this.searchHistoryList.removeAt(position)
        //데이터 덮어쓰기
        SharedPrefManager.storeSearchHistoryList(this.searchHistoryList)
        //데이터 변경 된 것을 알려줌
        this.mySearchHistoryRecyclerViewAdapter.notifyDataSetChanged()

        searchDeleteApiCall()
    }

    //검색 아이템 버튼 이벤트
    override fun onSearchItemClicked(position: Int) {
        Log.d(TAG,"SearchLookupFragment - onSearchItemClicked() called / position : $position")
        //해당 검색어의 api 호출
        val queryString : String = this.searchHistoryList[position].term

        searchAPICall(queryString)
       // viewBinding.searchMainBar.searchMainBar.title = queryString
        this.insertSearchTermHistory(searchTerm = queryString)
        //this.viewBinding.searchMainBar.searchMainBar.collapseActionView()
    }


    // 검색 api 호출
    private fun searchAPICall(query: String?) {
        searchRetrofit.recentsearch(jwt.toString()).enqueue(object : Callback<RecentSearchResponse> {
            override fun onResponse(call: Call<RecentSearchResponse>, response: Response<RecentSearchResponse>) {
                Log.d(ContentValues.TAG,"검색 api -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                when(response.body()!!.code) {
                    1000->{
                        val received = response.body()
                        searchHistoryIdx=received?.result?.search_history_idx!!.toLong()
                        val text : String = query.toString()
                        Log.e(TAG,"${text.toString()}")

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
        searchRetrofit.deletesearch(jwt.toString(), searchHistoryIdx).enqueue(object :Callback<DeleteSearchResponse>{
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
                        Log.e("검색삭제제 실패","${response.message()}")
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
        Log.d(TAG,"handleSearchViewUI() called / size : ${this.searchHistoryList.size}")
        if(this.searchHistoryList.size>0){
            search_history_recycler_view.visibility = View.VISIBLE
            search_history_recycler_view_label.visibility = View.VISIBLE
        }else{
            search_history_recycler_view.visibility = View.INVISIBLE
            search_history_recycler_view_label.visibility = View.INVISIBLE
        }
    }

    // 검색어 저장
    private fun insertSearchTermHistory(searchTerm: String){
        Log.d(TAG, "검색 - insertSearchTermHistory() called")

        if(SharedPrefManager.checkSearchHistoryMode() == true){
            // 중복 아이템 삭제
            var indexListToRemove = ArrayList<Int>()

            this.searchHistoryList.forEachIndexed{ index, searchDataItem ->

                if(searchDataItem.term == searchTerm){
                    Log.d(TAG, "index: $index")
                    indexListToRemove.add(index)
                }
            }

            indexListToRemove.forEach {
                this.searchHistoryList.removeAt(it)
            }

            // 새 아이템 넣기
            val newSearchData = SearchData(term = searchTerm, timestamp = Date().toSimpleString())
            this.searchHistoryList.add(newSearchData)

            // 기존 데이터에 덮어쓰기
            SharedPrefManager.storeSearchHistoryList(this.searchHistoryList)

            this.mySearchHistoryRecyclerViewAdapter.notifyDataSetChanged()
        }
    }
}