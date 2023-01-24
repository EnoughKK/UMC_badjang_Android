package com.umc.badjang.Bookmarks

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.HomeMorePage.PopularPostData
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentBookmarksAllBinding

// 즐겨찾기 > 전체
class BookmarksAllFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksAllBinding // viewBinding

    // 즐겨찾기 리스트 recyclerview adapter
    private val bookmarkAllDatas = mutableListOf<BookmarkItem>()
    private lateinit var bookmarkAllAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksAllBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recyclerview 세팅
        initRecycler()

        // 즐겨찾기 데이터 추가
        val profileImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.non_profile)
        val popularPostImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        val nationalNewsImg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.popular_post_img)
        for(i: Int in 0..5) {
            if(i%2 == 0) {
                addBookmarkData(
                    BookmarkPostData(
                        profileImg,
                        "익명",
                        "2022.12.28",
                        "자기추천 장학금 신청방법",
                        "자기추천 장학금 신청방법은 성적향상도(15), 진로탐색경험 (15),  대외활동(10), 자격층 취득(5), 지도교수상담(5) 총 50점 중 ...",
                        popularPostImg,
                        65,
                        215,
                        65
                    )
                )
            }
            else {
                addBookmarkData(
                    BookmarkScholarshipData(
                        "OO대학교",
                        nationalNewsImg,
                        "자기추천장학금",
                        "대상\n- 대학에 재학 중 또는 입,복학예정인 혼인 중이 아닌 무주택자",
                        65,
                        215
                    )
                )
            }
        }
    }

    // 즐겨찾기 리스트 recyclerview 세팅
    private fun initRecycler() {
        bookmarkAllAdapter = BookmarkAdapter(requireContext())
        viewBinding.bookmarksAllRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.bookmarksAllRecyclerview.adapter = bookmarkAllAdapter
        bookmarkAllAdapter.items = bookmarkAllDatas
    }

    // 즐겨찾기 리스트 데이터 추가
    private fun addBookmarkData(bookmarkItem: BookmarkItem) {
        bookmarkAllDatas.apply {
            add(bookmarkItem)
        }
        bookmarkAllAdapter.notifyDataSetChanged()
    }
}