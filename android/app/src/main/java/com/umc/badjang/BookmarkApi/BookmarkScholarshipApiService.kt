package com.umc.badjang.BookmarkApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

// 즐겨찾기(장학금) 조회 API
interface BookmarkScholarshipApiService {
    @GET("bookmark/scholarship")
    fun getBookmarkScholarship(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
    ): Call<BookmarkScholarshipApiData>
}