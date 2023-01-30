package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// 메인 페이지에 인기글 정보 받아오는 API
interface MainPopularApiService {
    @GET("menu/popular")
    fun getMainPopular(
        //@Header("post_idx") post_idx: Int,             // 게시글 인덱스
        //@Query("post_content") post_content: String,   // 내용
        //@Query("post_image") post_image: String,       // 이미지
        //@Query("post_createAt") post_createAt: String, // 생성 일시
        //@Query("post_updateAt") post_updateAt: String, // 변경 일시
        //@Query("post_status") post_status: String,     // 잔존여부
    ): Call<MainPopularApiData>
}