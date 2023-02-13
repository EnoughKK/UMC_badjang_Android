package com.umc.badjang.Bookmarks

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.BookmarkApi.BookmarkAllApiService
import com.umc.badjang.BookmarkApi.BookmarkAllData
import com.umc.badjang.BookmarkApi.BookmarkCheckScholarshipApiService
import com.umc.badjang.BookmarkApi.BookmarkResponseApiData
import com.umc.badjang.HomeMorePage.PopularPostData
import com.umc.badjang.HomePagaApi.*
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentBookmarksAllBinding
import com.umc.badjang.mConnectUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 즐겨찾기 > 전체
class BookmarksAllFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksAllBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    // 즐겨찾기 리스트 recyclerview adapter
    private val bookmarkAllDatas = mutableListOf<BookmarkItem>()
    private lateinit var bookmarkAllAdapter: BookmarkAdapter

    // 프로필 이미지
    var profileImg: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksAllBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // recyclerview 세팅
        initRecycler()

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 익명 프로필
        profileImg = BitmapFactory.decodeResource(resources, R.drawable.non_profile)

        // 즐겨찾기 전체 조회 api
        apiBookmarkAll()
    }

    // 즐겨찾기 리스트 recyclerview 세팅
    private fun initRecycler() {
        bookmarkAllAdapter = BookmarkAdapter(
            requireContext(),
            onClickScholarshipBookmark = {
                apiBookmarkScholarship(it)
            },
            true
        )
        viewBinding.bookmarksAllRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.bookmarksAllRecyclerview.adapter = bookmarkAllAdapter
        bookmarkAllAdapter.items = bookmarkAllDatas
    }

    // 즐겨찾기 리스트 게시물 데이터 추가
    private fun addBookmarkPostData(bookmarkPostItem: BookmarkPostDataString) {
        if(bookmarkPostItem.bookmarkPostImg == null) {
            bookmarkAllDatas.apply {
                add(BookmarkPostData(
                    bookmarkPostItem.bookmarkPostProfileImg,
                    bookmarkPostItem.bookmarkPostNickname,
                    bookmarkPostItem.bookmarkPostDate,
                    bookmarkPostItem.bookmarkPostTitle,
                    bookmarkPostItem.bookmarkPostContent,
                    null,
                    bookmarkPostItem.bookmarkPostCommentsCnt,
                    bookmarkPostItem.bookmarkPostViewCnt,
                    bookmarkPostItem.bookmarkPostGoodCnt
                ))
            }
            bookmarkAllAdapter.notifyDataSetChanged()
        }
        else {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap? = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(bookmarkPostItem.bookmarkPostImg)
                }

                bookmarkAllDatas.apply {
                    add(BookmarkPostData(
                        bookmarkPostItem.bookmarkPostProfileImg,
                        bookmarkPostItem.bookmarkPostNickname,
                        bookmarkPostItem.bookmarkPostDate,
                        bookmarkPostItem.bookmarkPostTitle,
                        bookmarkPostItem.bookmarkPostContent,
                        img,
                        bookmarkPostItem.bookmarkPostCommentsCnt,
                        bookmarkPostItem.bookmarkPostViewCnt,
                        bookmarkPostItem.bookmarkPostGoodCnt
                    ))
                }
                bookmarkAllAdapter.notifyDataSetChanged()
            }
        }

    }

    // 즐겨찾기 리스트 장학금, 지원금 데이터 추가
    private fun addBookmarkScholarshipData(bookmarkScholarshipItem: BookmarkScholarshipDataString) {
        if(bookmarkScholarshipItem.bookmarkScholarshipImg == null) {
            bookmarkAllDatas.apply {
                add(BookmarkScholarshipData(
                    bookmarkScholarshipItem.bookmarkScholarshipIdx,
                    bookmarkScholarshipItem.bookmarkScholarshipInstitution,
                    null,
                    bookmarkScholarshipItem.bookmarkScholarshipTitle,
                    bookmarkScholarshipItem.bookmarkScholarshipContent,
                    bookmarkScholarshipItem.bookmarkScholarshipCommentsCnt,
                    bookmarkScholarshipItem.bookmarkScholarshipViewCnt
                ))
            }
            bookmarkAllAdapter.notifyDataSetChanged()
        }
        else {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap? = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(bookmarkScholarshipItem.bookmarkScholarshipImg)
                }

                bookmarkAllDatas.apply {
                    add(BookmarkScholarshipData(
                        bookmarkScholarshipItem.bookmarkScholarshipIdx,
                        bookmarkScholarshipItem.bookmarkScholarshipInstitution,
                        img,
                        bookmarkScholarshipItem.bookmarkScholarshipTitle,
                        bookmarkScholarshipItem.bookmarkScholarshipContent,
                        bookmarkScholarshipItem.bookmarkScholarshipCommentsCnt,
                        bookmarkScholarshipItem.bookmarkScholarshipViewCnt
                    ))
                }
                bookmarkAllAdapter.notifyDataSetChanged()
            }
        }

    }

    // 장학금 즐겨찾기 추가 및 취소 api
    private fun apiBookmarkScholarship(scholarship: BookmarkScholarshipData) {
        retrofit!!.create(BookmarkCheckScholarshipApiService::class.java)
            .bookmarkScholarship(xAccessToken=jwt!!, scholarshipIdx=scholarship.bookmarkScholarshipIdx!!.toInt())
            .enqueue(object : Callback<BookmarkResponseApiData> {
                override fun onResponse(call: Call<BookmarkResponseApiData>, response: Response<BookmarkResponseApiData>) {
                    Log.d(ContentValues.TAG,"장학금 즐겨찾기 추가 및 취소 -------------------------------------------")
                    Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    //mySchoolDatas[position].bookmarkCheck = !mySchoolDatas[position].bookmarkCheck
                    //mySchoolAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<BookmarkResponseApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"장학금 즐겨찾기 추가 및 취소 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 즐겨찾기 전체 조회 api
    private fun apiBookmarkAll() {
        retrofit!!.create(BookmarkAllApiService::class.java).getBookmarkAll(xAccessToken=jwt!!)
            .enqueue(object : Callback<BookmarkAllData> {
                override fun onResponse(call: Call<BookmarkAllData>, response: Response<BookmarkAllData>) {
                    //Log.d(TAG,"즐겨찾기 전체 조회 -------------------------------------------")
                    //Log.d(TAG, "onResponse: ${response.body().toString()}")

                    // 게시글
                    val bookmarkBoardList = response.body()!!.result.getBookmarkBoardRes
                    for(i:Int in (0..bookmarkBoardList.size - 1)) {
                        addBookmarkPostData(
                            BookmarkPostDataString(
                                profileImg!!,
                                "익명",
                                " ",
                                bookmarkBoardList[i].post_name,
                                bookmarkBoardList[i].post_content,
                                bookmarkBoardList[i].post_image,
                                bookmarkBoardList[i].post_comment,
                                bookmarkBoardList[i].post_view,
                                bookmarkBoardList[i].post_recommend
                            )
                        )
                    }

                    // 장학금
                    val bookmarkScholarshipList = response.body()!!.result.getBookmarkScholarshipRes
                    for(i:Int in (0..bookmarkScholarshipList.size - 1)) {
                        addBookmarkScholarshipData(
                            BookmarkScholarshipDataString(
                                bookmarkScholarshipList[i].scholarship_idx,
                                bookmarkScholarshipList[i].scholarship_institution,
                                bookmarkScholarshipList[i].scholarship_image,
                                bookmarkScholarshipList[i].scholarship_name,
                                bookmarkScholarshipList[i].scholarship_content,
                                bookmarkScholarshipList[i].scholarship_comment,
                                bookmarkScholarshipList[i].scholarship_view
                            )
                        )
                    }

                    // 지원금
                    val bookmarkSupportList = response.body()!!.result.getBookmarkSupportRes
                    for(i:Int in (0..bookmarkSupportList.size - 1)) {
                        addBookmarkScholarshipData(
                            BookmarkScholarshipDataString(
                                null,
                                bookmarkSupportList[i].support_institution,
                                bookmarkSupportList[i].support_image,
                                bookmarkSupportList[i].support_name,
                                bookmarkSupportList[i].support_content,
                                bookmarkSupportList[i].support_comment,
                                bookmarkSupportList[i].support_view
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<BookmarkAllData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"즐겨찾기 전체 조회 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

}