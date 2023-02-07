package com.umc.badjang.Settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.badjang.MainActivity
import com.umc.badjang.databinding.FragmentPrivacyBinding
import com.umc.badjang.databinding.FragmentTermofuseBinding

class TermofUseFragment : Fragment() {
    private lateinit var viewBinding: FragmentTermofuseBinding // viewBinding

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
        viewBinding = FragmentTermofuseBinding.inflate(layoutInflater);
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding.SettingwebViewTerm){
            settings.javaScriptEnabled=true

            loadUrl("file:///android_asset/badjang_privacy.html")
            Log.d("개인정보 이용약관","loadUrl")
        }



        // 이전 버튼 선택 시
        viewBinding.SettingsTermUpBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}