package com.umc.badjang.MyPage

import com.umc.badjang.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MyProfileObject {
    private val myClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
        .build()

    val myRetrofit = initRetrofit()
    private const val URL = "https://prod.badjang2023.shop/"
    private fun initRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(URL)
            .client(myClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}