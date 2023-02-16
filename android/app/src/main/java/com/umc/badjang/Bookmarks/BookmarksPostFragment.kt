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
import com.umc.badjang.BookmarkApi.BookmarkPostApiData
import com.umc.badjang.BookmarkApi.BookmarkPostApiService
import com.umc.badjang.BookmarkApi.BookmarkScholarshipApiData
import com.umc.badjang.BookmarkApi.BookmarkScholarshipApiService
import com.umc.badjang.HomeMorePage.PopularPostAdapter
import com.umc.badjang.HomeMorePage.PopularPostData
import com.umc.badjang.HomePagaApi.ImageLoader
import com.umc.badjang.HomePagaApi.MainApiClient
import com.umc.badjang.HomePagaApi.PostIdxApiData
import com.umc.badjang.HomePagaApi.PostIdxApiService
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentBookmarksPostBinding
import com.umc.badjang.mConnectUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 즐겨찾기 > 게시글
class BookmarksPostFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookmarksPostBinding // viewBinding

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = null

    // 인기글 데이터 익명 사용자 프로필 사진
    var profileImg: Bitmap? = null

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

        // 현재 로그인 된 사용자 jwt 조회
        jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // recyclerview 세팅
        initRecycler()

        // 익명 프로필
        profileImg= BitmapFactory.decodeResource(resources, R.drawable.non_profile)

        // 즐겨찾기 게시글 조회 api
        apiBookmarkPost()
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

    // 즐겨찾기 게시글 조회 api
    private fun apiBookmarkPost() {
        retrofit!!.create(BookmarkPostApiService::class.java).getBookmarkPost(xAccessToken=jwt!!)
            .enqueue(object : Callback<BookmarkPostApiData> {
                override fun onResponse(call: Call<BookmarkPostApiData>, response: Response<BookmarkPostApiData>) {
                    Log.d(ContentValues.TAG,"즐겨찾기 게시글 조회 -------------------------------------------")
                    Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    val allBookmarkPost = response.body()!!.result

                    for(i: Int in (0..allBookmarkPost.size - 1)) {
                        apiPostData(allBookmarkPost[i].post_idx)
                    }

                }

                override fun onFailure(call: Call<BookmarkPostApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"즐겨찾기 게시글 조회 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 게시글 조회 api
    private fun apiPostData(postIdx: Int) {
        retrofit!!.create(PostIdxApiService::class.java).getPostData(mConnectUserId!!.toInt(), postIdx)
            .enqueue(object : Callback<PostIdxApiData> {
                override fun onResponse(call: Call<PostIdxApiData>, response: Response<PostIdxApiData>) {
                    //Log.d(ContentValues.TAG,"인기글 내용 -------------------------------------------")
                    //Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    val postData = response.body()!!.result[0]

                    // 익명
                    if(postData.post_anonymity == "Y") {
                        // 이미지 있음
                        if(postData.post_image != null) {
                            CoroutineScope(Dispatchers.Main).launch {
                                val img: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.post_image)
                                }
                                addBookmarksPostData(
                                    PopularPostData(
                                        profileImg!!,
                                        "익명",
                                        postData.post_createAt,
                                        postData.post_name,
                                        postData.post_content,
                                        img,
                                        postData.post_comment,
                                        postData.post_view,
                                        postData.post_recommend
                                    )
                                )
                            }
                        }
                        // 이미지 없음
                        else {
                            addBookmarksPostData(
                                PopularPostData(
                                    profileImg!!,
                                    "익명",
                                    postData.post_createAt,
                                    postData.post_name,
                                    postData.post_content,
                                    null,
                                    postData.post_comment,
                                    postData.post_view,
                                    postData.post_recommend
                                )
                            )
                        }
                    }
                    // 익명 아님
                    else {
                        // 이미지 있음
                        if(postData.post_image != null) {
                            CoroutineScope(Dispatchers.Main).launch {
                                val profile: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.user_profileimage_url!!)
                                }
                                val img: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.post_image)
                                }
                                addBookmarksPostData(
                                    PopularPostData(
                                        profile!!,
                                        postData.user_name,
                                        postData.post_createAt,
                                        postData.post_name,
                                        postData.post_content,
                                        img,
                                        postData.post_comment,
                                        postData.post_view,
                                        postData.post_recommend
                                    )
                                )
                            }
                        }
                        // 이미지 없음
                        else {
                            CoroutineScope(Dispatchers.Main).launch {
                                val profile: Bitmap? = withContext(Dispatchers.IO) {
                                    ImageLoader.loadImage(postData.user_profileimage_url!!)
                                }
                                addBookmarksPostData(
                                    PopularPostData(
                                        profile!!,
                                        postData.user_name,
                                        postData.post_createAt,
                                        postData.post_name,
                                        postData.post_content,
                                        null,
                                        postData.post_comment,
                                        postData.post_view,
                                        postData.post_recommend
                                    )
                                )
                            }
                        }
                    }

                    /*CoroutineScope(Dispatchers.Main).launch {
                        val img: Bitmap? = withContext(Dispatchers.IO) {
                            ImageLoader.loadImage(postData.post_image!!)
                        }
                    }*/
                }

                override fun onFailure(call: Call<PostIdxApiData>, t: Throwable) {
                    Log.d(ContentValues.TAG,"인기글 내용 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }
}