package com.umc.badjang.BookmarkApi

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarkCheckScholarshipApiService {
    @POST("scholarships/{scholarshipIdx}/bookmark")
    fun bookmarkScholarship(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
        @Path("scholarshipIdx") scholarshipIdx: Int
    ): Call<BookmarkResponseApiData>
}