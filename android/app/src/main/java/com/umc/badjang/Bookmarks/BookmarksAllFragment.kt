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
        addBookmarkData(
            BookmarkPostData(
                profileImg!!, "익명", "2022.12.28",
                "이번에 2월에 졸업하는 사람도 해당되나요?", "다가오는 2월에 졸업하는 사람도 장학금을 받을 수 있는지 궁금합니다!\n알려주세요 ㅠㅠ",
                null, 0, 1, 0
            )
        )
        addBookmarkData(
            BookmarkScholarshipData(
                "경상국립대학교", null, "재학생 장학금 - 개척",
                "- 최저기준\n" +
                "   - 직전학기 10학점 이상 이수하고, 직전학기 평균평점 3.0 이상인 자\n" +
                "   - 학과 추천에 의하여 선발 (학과별 자체 선정기준에 의함)\n", 1, 13
            )
        )
        /*for(i: Int in 0..5) {
            if(i%2 == 0) {
                addBookmarkData(
                    BookmarkPostData(
                        profileImg!!, "익명", "2022.12.28",
                        "이번에 2월에 졸업하는 사람도 해당되나요?", "다가오는 2월에 졸업하는 사람도 장학금을 받을 수 있는지 궁금합니다!\n알려주세요 ㅠㅠ",
                        null, 0, 1, 0
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
        }*/
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