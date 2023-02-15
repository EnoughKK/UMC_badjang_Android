package com.umc.badjang.BookmarkApi

import com.google.gson.annotations.SerializedName

data class BookmarkPostApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: MutableList<BookmarkPostApiResult>,     // 결과
)
data class BookmarkPostApiResult(
    @SerializedName("bookmark_idx")
    val bookmark_idx: Int,

    @SerializedName("post_idx")
    val post_idx: Int,

    @SerializedName("post_name")
    val post_name: String,

    @SerializedName("post_content")
    val post_content: String,

    @SerializedName("post_image")
    val post_image: String?,

    @SerializedName("post_view")
    val post_view: Int,

    @SerializedName("post_recommend")
    val post_recommend: Int,

    @SerializedName("post_comment")
    val post_comment: Int,

    @SerializedName("post_anonymity")
    val post_anonymity: String,
)