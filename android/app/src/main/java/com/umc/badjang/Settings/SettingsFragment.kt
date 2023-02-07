package com.umc.badjang.Settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.badjang.MainActivity
import com.umc.badjang.Settings.Logout.LogoutDialog
import com.umc.badjang.databinding.FragmentSettingsBinding
import retrofit2.Retrofit

class SettingsFragment : Fragment() {
    private lateinit var viewBinding: FragmentSettingsBinding // viewBinding

    //api 통신을 위한 retrofit
    private var retrofit : Retrofit? =null

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
        viewBinding = FragmentSettingsBinding.inflate(layoutInflater);
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.SettingsLogoutBtn.setOnClickListener {
            LogoutDialog(requireContext()).show()
        }
        viewBinding.SettingsMyInfoBtn.setOnClickListener{
            activity?.changeFragment(MyInfoFragment())
        }
        viewBinding.SettingsAlarmBtn.setOnClickListener{
            activity?.changeFragment(AlarmFragment())
        }


        // 이전 버튼 선택 시
        viewBinding.SettingsUpBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}



