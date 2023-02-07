package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MainMySchoolApiService {
    @GET("menu/school/{user_idx}")
    fun getMainMySchool(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
        @Path("user_idx") userIdx: Int
    ): Call<MainMySchoolApiData>
}
// MainMySchoolApiData