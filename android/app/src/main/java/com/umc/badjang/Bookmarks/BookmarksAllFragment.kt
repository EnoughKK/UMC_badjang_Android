package com.umc.badjang.Bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentBookmarksAllBinding

// 즐겨찾기 > 전체
class BookmarksAllFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksAllBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksAllBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}