package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainNationalNewsApiService {

    @GET("menu/total/{user_idx}")
    fun getMainNationalNews(
        @Path("user_idx") userIdx: Int
    ): Call<MainNationalNewsApiData>
}
// MainNationalNewsApiData