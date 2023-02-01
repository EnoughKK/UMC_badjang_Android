package com.umc.badjang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentMyPageBinding
import com.umc.badjang.databinding.FragmentSettingsBinding

class MyPageFragment : Fragment() {
    private lateinit var viewBinding: FragmentSettingsBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}