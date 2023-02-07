package com.umc.badjang

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.HomePage.HomeFragment
import com.umc.badjang.Settings.SettingsFragment
import com.umc.badjang.databinding.FragmentMyPageBinding
import retrofit2.Retrofit


class MyPageFragment : Fragment(){
    private lateinit var viewBinding: FragmentMyPageBinding // viewBinding

    //api 통신을 위한 retrofit
    private var retrofit :Retrofit? =null

    // 프래그먼트 전환을 위해
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
        viewBinding = FragmentMyPageBinding.inflate(layoutInflater);
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.MypageLlSetting.setOnClickListener{
            activity?.changeFragment(SettingsFragment())

        }

        viewBinding.MypageLlProfile.setOnClickListener {

        }

        // 이전 버튼 선택 시
        viewBinding.MypageUpBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


}
