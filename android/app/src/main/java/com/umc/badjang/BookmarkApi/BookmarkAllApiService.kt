package com.umc.badjang.BookmarkApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// 즐겨찾기(전체) 조회 API
interface BookmarkAllApiService {
    @GET("/bookmark")
    fun getBookmarkAll(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
    ): Call<BookmarkAllData>
}