package com.umc.badjang.Bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentBookmarksSupportBinding

// 즐겨찾기 > 지원금
class BookmarksSupportFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksSupportBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksSupportBinding.inflate(layoutInflater);

        return viewBinding.root
    }
}