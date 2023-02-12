package com.umc.badjang.BookmarkApi

import com.google.gson.annotations.SerializedName

data class BookmarkAllData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: BookmarkAllResult,     // 결과
)

data class BookmarkAllResult(
    @SerializedName("getBookmarkBoardRes")
    val getBookmarkBoardRes: MutableList<BookmarkBoard>, //게시판 즐겨찾기 리스트

    @SerializedName("getBookmarkScholarshipRes")
    val getBookmarkScholarshipRes: MutableList<BookmarkScholarship>, // 장학금 즐겨찾기 리스트

    @SerializedName("getBookmarkSupportRes")
    val getBookmarkSupportRes: MutableList<BookmarkSupport>, // 지원금 즐겨찾기 리스트
)

data class BookmarkBoard(
    @SerializedName("bookmark_idx")
    val bookmark_idx: Int,

    @SerializedName("post_idx")
    val post_idx: Int,

    @SerializedName("post_name")
    val post_name: String,

    @SerializedName("post_content")
    val post_content: String,

    @SerializedName("post_image")
    val post_image: String,

    @SerializedName("post_view")
    val post_view: Int,

    @SerializedName("post_recommend")
    val post_recommend: Int,

    @SerializedName("post_comment")
    val post_comment: Int,

    @SerializedName("post_anonymity")
    val post_anonymity: String, // 게시글 작성자 익명 여부
)

data class BookmarkScholarship(
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

data class BookmarkSupport(
    @SerializedName("bookmark_idx")
    val bookmark_idx: Int,

    @SerializedName("support_idx")
    val support_idx: Int,             // 지원금 인덱스

    @SerializedName("support_policy")
    val support_policy: String,       // 정책 id

    @SerializedName("support_name")
    val support_name: String,         // 지원금 이름

    @SerializedName("support_institution")
    val support_institution: String?,  // 주관기관

    @SerializedName("support_content")
    val support_content: String?,      // 내용

    @SerializedName("support_image")
    val support_image: String?,        // 이미지

    @SerializedName("support_homepage")
    val support_homepage: String?,     // 홈페이지

    @SerializedName("support_view")
    val support_view: Int,            // 조회수

    @SerializedName("support_comment")
    val support_comment: Int,         // 댓글수

    @SerializedName("support_scale")
    val support_scale: String?,        // 장학금규모

    @SerializedName("support_term")
    val support_term: String?,         // 신청기간

    @SerializedName("support_presentation")
    val support_presentation: String?, // 심사발표
)