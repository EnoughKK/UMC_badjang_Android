package com.umc.badjang.HomeMorePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentMySchoolBinding

// 홈화면 > 우리학교 장학금
class MySchoolFragment : Fragment() {
    private lateinit var viewBinding: FragmentMySchoolBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMySchoolBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}