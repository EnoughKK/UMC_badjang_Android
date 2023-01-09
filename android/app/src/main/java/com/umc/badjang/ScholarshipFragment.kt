package com.umc.badjang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentScholarshipBinding

class ScholarshipFragment : Fragment() {
    private lateinit var viewBinding: FragmentScholarshipBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScholarshipBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}