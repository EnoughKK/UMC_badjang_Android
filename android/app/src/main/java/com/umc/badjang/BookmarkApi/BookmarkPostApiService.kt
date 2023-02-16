package com.umc.badjang.BookmarkApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface BookmarkPostApiService {
    @GET("bookmark/board")
    fun getBookmarkPost(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
    ): Call<BookmarkPostApiData>
}