package com.umc.badjang.HomePagaApi

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// 인기글 api에서 받아온 데이터
@Keep
data class MainPopularApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: MutableList<MainPopularApiResult>, // 인기글 정보 list
)

@Keep
data class MainPopularApiResult(
    @SerializedName("post_idx")
    val post_idx: Int,         // 게시글 인덱스

    @SerializedName("post_content")
    val post_content: String,  // 내용

    @SerializedName("post_image")
    val post_image: String,    // 이미지

    @SerializedName("post_createAt")
    val post_createAt: String, // 생성 일시

    @SerializedName("post_updateAt")
    val post_updateAt: String, // 변경 일시

    @SerializedName("post_status")
    val post_status: String,   // 잔존여부
)