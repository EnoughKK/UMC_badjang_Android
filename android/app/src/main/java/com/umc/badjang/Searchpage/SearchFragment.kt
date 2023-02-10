package com.umc.badjang.Searchpage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentSearchresultAllBinding

class SearchFragment : Fragment() {
    private lateinit var viewBinding: FragmentSearchresultAllBinding// viewBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var  fragmentStateAdapter: FragmentStateAdapter

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
        viewBinding = FragmentSearchresultAllBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        //(activity as AppCompatActivity).setSupportActionBar(toolbar)

        initViewPager()

    }

    //검색했을 때 나오는 viewpager 초기화
    fun initViewPager() {

        viewPager = viewBinding.searchViewPager
        //fragmentStateAdapter = FragmentStateAdapter(requireActivity())
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

}