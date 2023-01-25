package com.umc.badjang.Bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.badjang.databinding.FragmentBookmarksScholarshipBinding

// 즐겨찾기 > 장학금
class BookmarksScholarshipFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksScholarshipBinding // viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksScholarshipBinding.inflate(layoutInflater);

        return viewBinding.root
    }

}