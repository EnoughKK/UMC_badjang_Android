package com.umc.badjang.Bookmarks

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

// 즐겨찾기 ViewPager 어댑터
class BookmarkViewPagerAdapter (manager: FragmentManager): FragmentPagerAdapter(manager) {

    var fragmentList: MutableList<Fragment> = arrayListOf()
    var titleList: MutableList<String> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
}