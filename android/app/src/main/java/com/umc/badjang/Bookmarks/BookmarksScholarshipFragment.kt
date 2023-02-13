package com.umc.badjang.Bookmarks

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.BookmarkApi.*
import com.umc.badjang.HomePagaApi.ImageLoader
import com.umc.badjang.HomePagaApi.MainApiClient
import com.umc.badjang.databinding.FragmentBookmarksScholarshipBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 즐겨찾기 > 장학금
class BookmarksScholarshipFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksScholarshipBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    // 즐겨찾기 리스트 recyclerview adapter
    private val bookmarkScholarshipDatas = mutableListOf<BookmarkItem>()
    private lateinit var bookmarkScholarshipAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookmarksScholarshipBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // recyclerview 세팅
        initRecycler()

        // 즐겨찾기 장학금 조회 api
        apiBookmarkScholarship()
    }

    // 즐겨찾기 리스트 recyclerview 세팅
    private fun initRecycler() {
        bookmarkScholarshipAdapter = BookmarkAdapter(
            requireContext(),
            onClickScholarshipBookmark = {
                apiBookmarkScholarship(it)
            },
            false
        )
        viewBinding.bookmarksScholarshipRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.bookmarksScholarshipRecyclerview.adapter = bookmarkScholarshipAdapter
        bookmarkScholarshipAdapter.items = bookmarkScholarshipDatas
    }

    // 즐겨찾기 리스트 장학금 데이터 추가
    private fun addBookmarkScholarshipData(bookmarkScholarshipItem: BookmarkScholarshipDataString) {
        if(bookmarkScholarshipItem.bookmarkScholarshipImg == null) {
            bookmarkScholarshipDatas.apply {
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
            bookmarkScholarshipAdapter.notifyDataSetChanged()
        }
        else {
            CoroutineScope(Dispatchers.Main).launch {
                val img: Bitmap? = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(bookmarkScholarshipItem.bookmarkScholarshipImg)
                }

                bookmarkScholarshipDatas.apply {
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
                bookmarkScholarshipAdapter.notifyDataSetChanged()
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

    // 즐겨찾기 장학금 조회 api
    private fun apiBookmarkScholarship() {
        retrofit!!.create(BookmarkScholarshipApiService::class.java).getBookmarkScholarship(xAccessToken=jwt!!)
            .enqueue(object : Callback<BookmarkScholarshipApiData> {
                override fun onResponse(call: Call<BookmarkScholarshipApiData>, response: Response<BookmarkScholarshipApiData>) {
                    //Log.d(TAG,"즐겨찾기 장학금 조회 -------------------------------------------")
                    //Log.d(TAG, "onResponse: ${response.body().toString()}")

                    // 장학금
                    val bookmarkScholarshipList = response.body()!!.result
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
                }

                override fun onFailure(call: Call<BookmarkScholarshipApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"즐겨찾기 장학금 조회 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }
}