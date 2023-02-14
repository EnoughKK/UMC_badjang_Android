package com.umc.badjang.BookmarkApi

import com.google.gson.annotations.SerializedName

data class BookmarkScholarshipApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: MutableList<BookmarkScholarshipResult>,     // 결과
)

data class BookmarkScholarshipResult(
    @SerializedName("bookmark_idx")
    val bookmark_idx: Long,

    @SerializedName("scholarship_idx")
    val scholarship_idx: Long,            // 장학금 인덱스

    @SerializedName("scholarship_name")
    val scholarship_name: String,         // 장학금 이름

    @SerializedName("scholarship_institution")
    val scholarship_institution: String?,  // 주관기관

    @SerializedName("scholarship_content")
    val scholarship_content: String?,      // 내용

    @SerializedName("scholarship_image")
    val scholarship_image: String?,        // 이미지

    @SerializedName("scholarship_homepage")
    val scholarship_homepage: String?,     // 홈페이지

    @SerializedName("scholarship_view")
    val scholarship_view: Int,            // 조회수

    @SerializedName("scholarship_comment")
    val scholarship_comment: Int,         // 댓글수

    @SerializedName("scholarship_scale")
    val scholarship_scale: String?,        // 장학금규모

    @SerializedName("scholarship_term")
    val scholarship_term: String?,         // 신청기간

    @SerializedName("scholarship_presentation")
    val scholarship_presentation: String, // 심사발표
)
