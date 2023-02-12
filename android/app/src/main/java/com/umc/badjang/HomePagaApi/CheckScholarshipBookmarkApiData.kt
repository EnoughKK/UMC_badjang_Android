package com.umc.badjang.HomePagaApi

import com.google.gson.annotations.SerializedName

data class CheckScholarshipBookmarkApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: CheckScholarshipBookmarkResult,     // 결과
)

data class CheckScholarshipBookmarkResult(
    @SerializedName("scholarship_idx")
    val scholarship_idx: Int,   // 장학금 idx

    @SerializedName("bookmark_check")
    val bookmark_check: String, // 즐겨찾기 여부(Y/N)
)