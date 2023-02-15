package com.umc.badjang.PostWritePage

import com.google.gson.annotations.SerializedName

data class SchoolPostWriteApiData(
    @SerializedName("post_name")
    val post_name: String,         // 제목

    @SerializedName("post_content")
    val post_content: String,      // 내용

    @SerializedName("post_image")
    val post_image: String? = null,       // 이미지

    @SerializedName("post_anonymity")
    val post_anonymity: String,    // 익명 유무
)

data class SchoolPostWriteApiResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 요청 성공 여부

    @SerializedName("code")
    val code: Int,           // 응답 코드

    @SerializedName("message")
    val message: String,     // 응답 메시지
)