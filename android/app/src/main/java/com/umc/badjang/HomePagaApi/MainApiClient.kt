package com.umc.badjang.HomePagaApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

// 메인 페이지에 인기글 정보 받아오는 API
object MainApiClient {
    private const val BASE_URL = "https://prod.badjang2023.shop/"

    val mainApiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}