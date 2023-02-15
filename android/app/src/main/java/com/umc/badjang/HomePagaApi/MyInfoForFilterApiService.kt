package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyInfoForFilterApiService {
    @GET("mypage")
    fun getMyInfoForFilter(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
    ): Call<MyInfoForFilterApiData>
}