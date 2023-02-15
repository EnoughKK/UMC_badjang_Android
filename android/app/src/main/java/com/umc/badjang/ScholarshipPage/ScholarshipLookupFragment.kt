package com.umc.badjang.ScholarshipPage

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umc.badjang.ApplicationClass
import com.umc.badjang.Bookmarks.BookmarksFragment
import com.umc.badjang.HomeMorePage.NewIssueFragment
import com.umc.badjang.HomePagaApi.MainApiClient
import com.umc.badjang.HomePagaApi.MyInfoForFilterApiData
import com.umc.badjang.HomePagaApi.MyInfoForFilterApiResult
import com.umc.badjang.HomePagaApi.MyInfoForFilterApiService
import com.umc.badjang.HomePage.*
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.Model.ScholarshipFilterDTO
import com.umc.badjang.databinding.DialogScholarshipLookupBinding
import com.umc.badjang.databinding.FragmentScholarshipLookupBinding
import com.umc.badjang.mConnectUserId
import com.umc.badjang.utils.RESPONSE_STATE
import kotlinx.android.synthetic.main.fragment_scholarship_lookup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class ScholarshipLookupFragment: Fragment() {
    private lateinit var viewBinding: FragmentScholarshipLookupBinding // viewBinding
    private lateinit var viewPager: ViewPager2
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

        // Toolbar
//        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
//        (activity as AppCompatActivity).setSupportActionBar(toolbar) //커스텀한 toolbar를 액션바로 사용

        // 즐겨찾기 버튼 선택
//        val bookmarksBtn: ImageButton = toolbar.findViewById(R.id.toolbar_star_btn)
//        bookmarksBtn.setOnClickListener {
//            activity?.changeFragment(BookmarksFragment())
//        }

        // 알림 버튼 선택
//        val newIssueBtn: ImageButton = toolbar.findViewById(R.id.toolbar_bell_btn)
//        newIssueBtn.setOnClickListener {
//            activity?.changeFragment(NewIssueFragment())
//        }

        // 플로팅 버튼
        viewBinding.floatingBtnSearchFilter.setOnClickListener {

            // 다이얼로그 화면
            ScholarshipFilterDialog(requireContext(), requireActivity()).show()
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