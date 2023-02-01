package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET

interface MainNationalNewsApiService {

    @GET("menu/total/54")
    fun getMainNationalNews(
    ): Call<MainNationalNewsApiData>
}