package com.umc.badjang.Bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.umc.badjang.databinding.FragmentBookmarksBinding


// 홈화면 > 즐겨찾기
class BookmarksFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksBinding // viewBinding

    // viewPager
    lateinit var viewPagers: ViewPager
    lateinit var tabLayouts: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewPager 세팅
        initViewPager()

        tabLayouts.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })

        // 이전 버튼 선택 시
        viewBinding.bookmarksBackBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    // viewPager 세팅
    private fun initViewPager() {
        viewPagers = viewBinding.bookmarksViewpager
        tabLayouts = viewBinding.bookmarksTablayout

        var adapter = BookmarkViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(BookmarksAllFragment(), "전체")
        adapter.addFragment(BookmarksScholarshipFragment(), "장학금")
        adapter.addFragment(BookmarksSupportFragment(), "지원금")
        adapter.addFragment(BookmarksPostFragment(), "게시글")

        viewPagers!!.adapter = adapter
        tabLayouts!!.setupWithViewPager(viewPagers)

    }

}