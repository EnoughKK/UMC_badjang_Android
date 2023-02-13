package com.umc.badjang.Searchpage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Searchpage.models.SearchData
import com.umc.badjang.databinding.FragmentSearchLookupBinding
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList

class SearchLookupFragment : Fragment(), SearchView.OnQueryTextListener,View.OnClickListener {
    private lateinit var viewBinding: FragmentSearchLookupBinding// viewBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    //어댑터
    private lateinit var  fragmentStateAdapter: FragmentStateAdapter

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

        val search_toolbar: Toolbar = requireActivity().findViewById(R.id.search_mainbar)
        (activity as AppCompatActivity).setSupportActionBar(search_toolbar)

        initViewPager()


        // 저장된 검색 기록 가져오기
        this.searchHistoryList = SharedPrefManager.getSearchHistoryList() as ArrayList<SearchData>

        this.searchHistoryList.forEach {
            Log.d("검색기록", "저장된 검색 기록 - it.term : ${it.term} , it.timestamp: ${it.timestamp}")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
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
            val newSearchData = SearchData(term = query, timestamp = Date().toString())
            this.searchHistoryList.add(newSearchData)
            SharedPrefManager.storeSearchHistoryList(this.searchHistoryList)
        }

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

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}