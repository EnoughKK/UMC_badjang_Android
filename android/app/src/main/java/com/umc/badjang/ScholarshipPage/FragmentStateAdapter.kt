package com.umc.badjang.ScholarshipPage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class FragmentStateAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    // 1. ViewPager 에 연결할 Fragment 들을 생성
    var fragmentList = listOf<Fragment>(ScholarshipViewpager1Fragment(), SubsidyViewpager2Fragment())

    // 2. ViesPager 에서 노출시킬 Fragment 의 갯수 설정
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // 3. ViewPager 의 각 페이지에서 노출할 Fragment 설정
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}