package com.umc.badjang.HomeMorePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentBookmarksBinding

// 홈화면 > 즐겨찾기
class BookmarksFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}