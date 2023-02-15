package com.umc.badjang.Searchpage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.ScholarshipPage.ScholarshipRVAdapter
import com.umc.badjang.databinding.FragmentSearchBinding

class RecentSearchFragment:Fragment() {

    private lateinit var viewBinding: FragmentSearchBinding

    var activity: MainActivity? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentSearchBinding.inflate(layoutInflater);


        return viewBinding.root
    }
}

