package com.umc.badjang.HomeMorePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentPopularPostBinding

// 홈화면 > 인기글
class PopularPostFragment : Fragment() {
    private lateinit var viewBinding: FragmentPopularPostBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPopularPostBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}