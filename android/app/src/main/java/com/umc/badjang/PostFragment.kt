package com.umc.badjang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentPostBinding

class PostFragment : Fragment() {
    private lateinit var viewBinding: FragmentPostBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPostBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}