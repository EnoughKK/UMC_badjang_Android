package com.umc.badjang.Bookmarks

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.HomeMorePage.PopularPostAdapter
import com.umc.badjang.HomeMorePage.PopularPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentBookmarksPostBinding

// 즐겨찾기 > 게시글
class BookmarksPostFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksPostBinding // viewBinding

    // 게시글 리스트 recyclerview adapter
    private val bookmarksPostDatas = mutableListOf<PopularPostData>()
    private lateinit var bookmarksPostAdapter: PopularPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksPostBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recyclerview 세팅
        initRecycler()

        // 게시글 데이터 추가
        val profileImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.non_profile)
        val popularPostImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in 0..0) {
            addBookmarksPostData(
                PopularPostData(profileImg!!, "익명", "2022.12.28",
                    "이번에 2월에 졸업하는 사람도 해당되나요?", "다가오는 2월에 졸업하는 사람도 장학금을 받을 수 있는지 궁금합니다!\n알려주세요 ㅠㅠ",
                    null, 0, 1, 0))
        }
    }

    // 즐겨찾기 > 게시글 recyclerview 세팅
    private fun initRecycler() {
        bookmarksPostAdapter = PopularPostAdapter(requireContext())
        viewBinding.bookmarksPostRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.bookmarksPostRecyclerview.adapter = bookmarksPostAdapter
        bookmarksPostAdapter.datas = bookmarksPostDatas
    }

    // 게시글 데이터 추가
    private fun addBookmarksPostData(bookmarksPost: PopularPostData) {
        bookmarksPostDatas.apply {
            add(bookmarksPost)
        }
        bookmarksPostAdapter.notifyDataSetChanged()
    }

}