package com.umc.badjang.BookmarkApi

import com.google.gson.annotations.SerializedName

data class BookmarkResponseApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지
)
