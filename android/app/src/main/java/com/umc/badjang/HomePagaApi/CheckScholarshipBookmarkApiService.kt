package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// 장학금 즐겨찾기 유무 판단
interface CheckScholarshipBookmarkApiService {
    @GET("scholarships/{scholarshipIdx}/bookmark_check")
    fun checkScholarshipBookmark(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
        @Path("scholarshipIdx") scholarshipIdx: Int
    ): Call<CheckScholarshipBookmarkApiData>
}