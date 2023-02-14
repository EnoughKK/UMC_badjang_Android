package com.umc.badjang.PostWritePage

import com.google.gson.annotations.SerializedName

// 서버에 저장할 게시글 작성 정보
data class PostWriteApiBody(
    @SerializedName("user_idx")
    val user_idx: Int,             // 유저 번호

    @SerializedName("post_category")
    val post_category: String,     // 카테고리

    @SerializedName("post_name")
    val post_name: String,         // 제목

    @SerializedName("post_content")
    val post_content: String,      // 내용

    @SerializedName("post_image")
    val post_image: String? = null,       // 이미지

    @SerializedName("post_anonymity")
    val post_anonymity: String,    // 익명 유무
)

// 학교, 지역 정보를 전달하고 받을 값
data class PostWriteApiResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 요청 성공 여부

    @SerializedName("code")
    val code: Int,           // 응답 코드

    @SerializedName("message")
    val message: String,     // 응답 메시지

    @SerializedName("result")
    val result: MutableList<PostWriteApiResult>
)

data class PostWriteApiResult(
    @SerializedName("post_idx")
    val post_idx: Int,             // 게시글 번호

    @SerializedName("user_idx")
    val user_idx: Int,             // 유저 번호

    @SerializedName("post_category")
    val post_category: String,     // 카테고리

    @SerializedName("post_content")
    val post_content: String,      // 내용

    @SerializedName("post_createAt")
    val post_createAt: String,     // 생성 시간
)
