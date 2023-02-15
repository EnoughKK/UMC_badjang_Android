package com.umc.badjang.PostWritePage

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SchoolPostWriteApiService {
    @POST("board/school/{schoolNameIdx}/add")
    fun addSchoolPost(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
        @Path("schoolNameIdx") schoolNameIdx: Int,
        @Body params: SchoolPostWriteApiData
    ): Call<SchoolPostWriteApiResponse>
}