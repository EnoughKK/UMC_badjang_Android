package com.umc.badjang.HomeMorePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentNationalNewsBinding

// 홈화면 > 전국소식
class NationalNewsFragment : Fragment() {
    private lateinit var viewBinding: FragmentNationalNewsBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNationalNewsBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}