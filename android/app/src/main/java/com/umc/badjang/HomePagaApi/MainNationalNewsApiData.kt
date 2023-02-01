package com.umc.badjang.HomePagaApi

import com.google.gson.annotations.SerializedName

// 전국소식 api에서 받아온 데이터
data class MainNationalNewsApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: MutableList<MainNationalNewsApiResult>, // 전국소식 정보 list
)

data class MainNationalNewsApiResult(
    @SerializedName("scholarship_idx")
    val scholarship_idx: Int,             // 장학금 인덱스

    @SerializedName("scholarship_name")
    val scholarship_name: String,         // 장학금 이름

    @SerializedName("scholarship_institution")
    val scholarship_institution: String,  // 주관기관

    @SerializedName("scholarship_content")
    val scholarship_content: String,      // 내용

    @SerializedName("scholarship_image")
    val scholarship_image: String,        // 이미지

    @SerializedName("scholarship_homepage")
    val scholarship_homepage: String,     // 홈페이지

    @SerializedName("scholarship_view")
    val scholarship_view: Int,            // 조회수

    @SerializedName("scholarship_comment")
    val scholarship_comment: Int,         // 댓글수

    @SerializedName("scholarship_scale")
    val scholarship_scale: String,        // 장학금규모

    @SerializedName("scholarship_term")
    val scholarship_term: String,         // 신청기간

    @SerializedName("scholarship_presentation")
    val scholarship_presentation: String, // 심사발표

    @SerializedName("scholarship_createAt")
    val scholarship_createAt: String,     // 작성시간

    @SerializedName("scholarship_updateAt")
    val scholarship_updateAt: String,     // 수정시간

    @SerializedName("scholarship_status")
    val scholarship_status: String,       // 상태

    @SerializedName("scholarship_univ")
    val scholarship_univ: String,         // 학교

    @SerializedName("scholarship_college")
    val scholarship_college: String,      // 단과대학

    @SerializedName("scholarship_department")
    val scholarship_department: String,   // 학과

    @SerializedName("scholarship_grade")
    val scholarship_grade: String,        // 학년

    @SerializedName("scholarship_semester")
    val scholarship_semester: String,      // 학기

    @SerializedName("scholarship_province")
    val scholarship_province: String,      // 도

    @SerializedName("scholarship_city")
    val scholarship_city: String,          // 시/군/구

    @SerializedName("scholarship_category")
    val scholarship_category: String,      // 카테고리

    @SerializedName("bookmark_post")
    val bookmark_post: Int,                // 즐겨찾기 여부
)