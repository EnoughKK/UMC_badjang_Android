package com.umc.badjang

import android.app.ActivityManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

//develop 브랜치 추가
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umc.badjang.HomePage.HomeFragment
import com.umc.badjang.PostPage.PostFragment
import com.umc.badjang.ScholarshipPage.Model.GetScholarshipDTO
import com.umc.badjang.ScholarshipPage.Model.ScholarshipFilterDTO
import com.umc.badjang.ScholarshipPage.ScholarshipLookupFragment
import com.umc.badjang.ScholarshipPage.ScholarshipViewpager1Fragment
import com.umc.badjang.Settings.AlarmFragment
import com.umc.badjang.Settings.MyInfoFragment
import com.umc.badjang.databinding.ActivityMainBinding

public var mConnectUserId: Int? = null
public var mScholarshipDatas = ArrayList<GetScholarshipDTO>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding // viewBinding

    private var doubleBackToExit = false
    // 이전 버튼 - 폰에 있는 이전 버튼
    override fun onBackPressed() {
        //super.onBackPressed()

        if (doubleBackToExit) {
            finishAffinity()
        } else {
            // 현재 액티비티
            val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val info = manager.getRunningTasks(1)
            val componentName = info[0].topActivity
            val ActivityName = componentName!!.shortClassName.substring(1)

            // 메인 액티비티인 경우
            if(ActivityName == "MainActivity") {
                var currentFragment: Fragment? = null
                var cntFragment: Int = 0

                // 현재 프래그먼트 찾기
                for (fragment: Fragment in supportFragmentManager.fragments) {
                    if (fragment.isVisible) {
                        currentFragment = fragment
                        cntFragment++
                    }
                }

                // 현재 프래그먼트가 각 fragment의 첫 페이지 중 하나가 아닌 경우
                if (cntFragment > 1) {
                    // 이전 페이지로 이동
                    supportFragmentManager.beginTransaction().remove(currentFragment!!).commit()
                    supportFragmentManager.popBackStack()
                }
                // 현재 프래그먼트가 각 fragment의 첫 페이지 중 하나인 경우
                else {
                    Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                    doubleBackToExit = true
                    runDelayed(1500L) {
                        doubleBackToExit = false
                    }
                }
            }
        }
    }
    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 하단 메뉴바를 위한 fragment
        supportFragmentManager.beginTransaction().replace(binding.fragmentLayout.id, HomeFragment()).commit()
        // navigationBottomView 등록: 하단바 fragment id(bottom_navigation) 등록
        transitionNavigationBottomView(binding.bottomNavigation, supportFragmentManager)
    }

    // NavigationBottomView 화면 전환하는 함수.
    private fun transitionNavigationBottomView(bottomView: BottomNavigationView, fragmentManager: FragmentManager){
        bottomView.setOnItemSelectedListener {
            it.isChecked = true
            when(it.itemId){
                R.id.home -> {
                    fragmentManager.beginTransaction().replace(binding.fragmentLayout.id, HomeFragment()).commit()
                }
                R.id.scholarship -> {
                    fragmentManager.beginTransaction().replace(binding.fragmentLayout.id, ScholarshipLookupFragment()).commit()
                }
                R.id.post -> {
                    fragmentManager.beginTransaction().replace(binding.fragmentLayout.id, PostFragment()).commit()
                }
                R.id.search -> {
                    fragmentManager.beginTransaction().replace(binding.fragmentLayout.id, SearchFragment()).commit()
                }
                R.id.mypage -> {
                    fragmentManager.beginTransaction().replace(binding.fragmentLayout.id, MyPageFragment()).commit()
                }
                else -> Log.d("test", "error") == 0
            }
            Log.d("test", "final") == 0
        }
    }

    // fragment 전환
    fun changeFragment(fragment: Fragment){
        // 이전페이지로 돌아가는 기능을 이용할 수 있도록 replace가 아니라 add로
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentLayout.id, fragment)
            .commit()
    }

    // fragment data 전송, 전환
    fun SendDataFragment(fragment: Fragment, itemIdx: Long, scholarshipName: String){

        val bundle = Bundle()
        bundle.putString("ScholarshipName", scholarshipName)
        bundle.putLong("ItemIdx", itemIdx)
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentLayout.id, fragment)
            .commit()
    }

}