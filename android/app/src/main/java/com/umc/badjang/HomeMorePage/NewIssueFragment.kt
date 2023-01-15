package com.umc.badjang.HomeMorePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentNewIssueBinding

// 홈화면 > 알림
class NewIssueFragment : Fragment() {
    private lateinit var viewBinding: FragmentNewIssueBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewIssueBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}