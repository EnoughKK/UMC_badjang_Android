package com.umc.badjang.HomePagaApi

import com.google.gson.annotations.SerializedName

data class PostIdxApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: MutableList<PostIdxApiResult>, // 게시글 정보 list
)


data class PostIdxApiResult(
    @SerializedName("post_idx")
    val post_idx: Int,         // 게시글 인덱스

    @SerializedName("user_idx")
    val user_idx: Int,         // 사용자 인덱스

    @SerializedName("post_category")
    val post_category: String, // 카테고리

    @SerializedName("post_name")
    val post_name: String,     // 제목

    @SerializedName("post_content")
    val post_content: String,  // 내용

    @SerializedName("post_image")
    val post_image: String?,    // 이미지

    @SerializedName("post_view")
    val post_view: Int,         // 조회수

    @SerializedName("post_recommend")
    val post_recommend: Int,   // 추천수

    @SerializedName("post_comment")
    val post_comment: Int,     // 댓글수

    @SerializedName("post_createAt")
    val post_createAt: String, // 생성 일시

    @SerializedName("post_updateAt")
    val post_updateAt: String, // 변경 일시

    @SerializedName("post_status")
    val post_status: String,   // 글 상태

    @SerializedName("post_anonymity")
    val post_anonymity: String, // 익명 여부

    @SerializedName("school_name_idx")
    val school_name_idx: Int,   // 학교 인덱스

    @SerializedName("post_bookmark")
    val post_bookmark: Int,     // 즐겨찾기 인덱스

    @SerializedName("user_name")
    val user_name: String,      // 유저 이름

    @SerializedName("user_profileimage_url")
    val user_profileimage_url: String?, // 유저 이미지
)