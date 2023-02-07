package com.umc.badjang.Settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.badjang.MainActivity
import com.umc.badjang.databinding.FragmentMyinfoBinding
import retrofit2.Retrofit

class MyInfoFragment :Fragment() {
    private lateinit var viewBinding: FragmentMyinfoBinding // viewBinding

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
        viewBinding = FragmentMyinfoBinding.inflate(layoutInflater);
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이전 버튼 선택 시
        viewBinding.MyInfoUpBtn.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        //서비스 이용약관 클릭시
        viewBinding.MyInfoTermofuseBtn.setOnClickListener{
            activity?.changeFragment(TermofUseFragment())
        }

        //개인정보 이용약관 클릭시

        viewBinding.MyInfoPrivacyBtn.setOnClickListener{
            activity?.changeFragment(PrivacyFragment())
        }
    }


}
