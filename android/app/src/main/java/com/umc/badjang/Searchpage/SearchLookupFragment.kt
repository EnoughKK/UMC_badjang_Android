package com.umc.badjang.Searchpage

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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

class SearchLookupFragment : Fragment(), SearchView.OnQueryTextListener,View.OnClickListener,
    ISearchHistroyRecyclerView {
    private lateinit var viewBinding: FragmentSearchLookupBinding// viewBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    //어댑터 - 유저데이터 들어갈 리싸이클러뷰 어뎁터
    private lateinit var  fragmentStateAdapter: SFragmentStateAdapter


    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

    val text = ApplicationClass.cSharedPreferences.getString(ApplicationClass.QUREY_TEXT,null)

    val searchRetrofit= ApplicationClass.sRetrofit.create(SearchRetrofit::class.java)

    //유저 검색 기록 배열
    //private var SearchUserArrayList = ArrayList<SearchUser>()

    // 검색 기록 배열
    private var searchHistoryList = ArrayList<SearchData>()
    private lateinit var  mySearchHistoryRecyclerViewAdapter: SearchHistoryRecyclerViewAdapter


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
       //툴바 적용
        //val myToolbar = viewBinding.searchMainBar
       // (context as AppCompatActivity).setSupportActionBar(myToolbar)
        //setHasOptionsMenu(true)

        // 저장된 검색 기록 가져오기
        this.searchHistoryList = SharedPrefManager.getSearchHistoryList() as ArrayList<SearchData>

        this.searchHistoryList.forEach {
            Log.d("검색기록", "저장된 검색 기록 - it.term : ${it.term} , it.timestamp: ${it.timestamp}")
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색 리사이클러뷰 준비
        this.searchHistoryRecyclerViewSetting(this.searchHistoryList)


        initViewPager()
    }

    //검색 기록 리사이클러뷰 준비
    private fun searchHistoryRecyclerViewSetting(searchHistoryList: ArrayList<SearchData>){
        Log.d(TAG,"검색 프레그먼트 - searchHistoryRecyclerViewSetting() called")

        this.mySearchHistoryRecyclerViewAdapter = SearchHistoryRecyclerViewAdapter(this)
        this.mySearchHistoryRecyclerViewAdapter.submitList(searchHistoryList)

        //리니어 레이아웃으로 가져오기
        val myLinearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        myLinearLayoutManager.stackFromEnd=true //최근 검색어가 가장 위에 쌓임

        search_history_recycler_view.apply{
            layoutManager = myLinearLayoutManager
            this.scrollToPosition(mySearchHistoryRecyclerViewAdapter.itemCount - 1)
            adapter = mySearchHistoryRecyclerViewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_top_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(TAG, "onCreateOptionMenu")

        this.mySearchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력하세요."
            this.setOnQueryTextFocusChangeListener { view, hasExpanded ->
                when (hasExpanded) {
                    true -> {
                        Log.d(TAG, "서치뷰 열림")
                        search_history_recycler_view.visibility = View.VISIBLE
                    }
                    false -> {
                        Log.d(TAG, "서치뷰 닫힘")
                        search_history_recycler_view.visibility = View.INVISIBLE
                    }
                }
                this.setOnQueryTextListener(this@SearchLookupFragment)
             mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
            mySearchViewEditText.apply {
                this.filters = arrayOf(InputFilter.LengthFilter(50))
                this.setTextColor(Color.BLACK)
                this.setHintTextColor(Color.GRAY)
            }
        }
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("검색입력","onQueryTextSubmit() : $query")

        if (!query.isNullOrEmpty()){

            this.insertSearchTermHistory(query)
            this.searchAPICall(query)
        }

        //this.viewBinding.searchMainBar.collapseActionView()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("검색입력","onQueryTextChange() : $newText")

        val userInputText = newText.let{
            it
        }?: ""

        if(userInputText.count()==50){
            Toast.makeText(ApplicationClass.instance,"검색어는 50자까지 입력 가능합니다!",Toast.LENGTH_SHORT).show()
        }
        return true
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
        this.insertSearchTermHistory(searchTerm = queryString)
        search_history_recycler_view.visibility=View.INVISIBLE
        //this.viewBinding.searchMainBar.collapseActionView()
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

    override fun onClick(view: View?) {

    }
}