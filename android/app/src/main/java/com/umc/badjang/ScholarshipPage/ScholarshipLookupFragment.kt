package com.umc.badjang.ScholarshipPage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentScholarshipLookupBinding

class ScholarshipLookupFragment: Fragment() {
    private lateinit var viewBinding: FragmentScholarshipLookupBinding // viewBinding

    private lateinit var viewPager: ViewPager2
    //네개의 항목을 위한 tablayout
    private lateinit var tabLayout: TabLayout
    private lateinit var fragmentStateAdapter: FragmentStateAdapter


    var activity: MainActivity? = null

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
        viewBinding = FragmentScholarshipLookupBinding.inflate(layoutInflater);

        // 플로팅 버튼
        viewBinding.floatingBtnSearchFilter.setOnClickListener {

        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        initViewpager()
    }

    // viewpager 셋팅
    fun initViewpager() {

        viewPager = viewBinding.viewPager
        fragmentStateAdapter = FragmentStateAdapter(requireActivity())
        viewPager.adapter = fragmentStateAdapter

        tabLayout = viewBinding.tabLayout
        viewPager.setUserInputEnabled(false)

        // 탭 레이아웃 연결
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            when (position) {
                0 -> tab.text = "장학금"
                1 -> tab.text = "지원금"
            }
        }.attach()
    }
}