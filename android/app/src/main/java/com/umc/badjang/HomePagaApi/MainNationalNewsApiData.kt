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
    val result: MainNationalNewsApiResult, // 전국소식 정보 list
)

data class MainNationalNewsApiResult(
    @SerializedName("scholarshipList")
    val scholarshipList: MutableList<MainNationalNewsScholarship>, // 장학금 데이터

    @SerializedName("supportList")
    val supportList: MutableList<MainNationalNewsSupport>, // 지원금 데이터
)

// 장학금
data class MainNationalNewsScholarship(
    @SerializedName("scholarship_idx")
    val scholarship_idx: Int,             // 장학금 인덱스

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

    @SerializedName("scholarship_createAt")
    val scholarship_createAt: String,     // 작성시간

    @SerializedName("scholarship_updateAt")
    val scholarship_updateAt: String,     // 수정시간

    @SerializedName("scholarship_status")
    val scholarship_status: String,       // 상태

    @SerializedName("scholarship_univ")
    val scholarship_univ: String?,         // 학교

    @SerializedName("scholarship_college")
    val scholarship_college: String?,      // 단과대학

    @SerializedName("scholarship_department")
    val scholarship_department: String?,   // 학과

    @SerializedName("scholarship_grade")
    val scholarship_grade: String?,        // 학년

    @SerializedName("scholarship_semester")
    val scholarship_semester: String?,      // 학기

    @SerializedName("scholarship_province")
    val scholarship_province: String?,      // 도

    @SerializedName("scholarship_city")
    val scholarship_city: String?,          // 시/군/구

    @SerializedName("scholarship_category")
    val scholarship_category: String,      // 카테고리

    @SerializedName("bookmark_post")
    val bookmark_post: Int,                // 즐겨찾기 여부
)

// 지원금
data class MainNationalNewsSupport(
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

    @SerializedName("support_createAt")
    val support_createAt: String,     // 작성시간

    @SerializedName("support_updateAt")
    val support_updateAt: String,     // 수정시간

    @SerializedName("support_status")
    val support_status: String,       // 상태

    @SerializedName("support_province")
    val support_province: String?,      // 도

    @SerializedName("support_city")
    val support_city: String?,          // 시/군/구

    @SerializedName("support_univ")
    val support_univ: String?,         // 학교

    @SerializedName("support_college")
    val support_college: String?,      // 단과대학

    @SerializedName("support_department")
    val support_department: String?,   // 학과

    @SerializedName("support_grade")
    val support_grade: String?,        // 학년

    @SerializedName("support_semester")
    val support_semester: String?,      // 학기

    @SerializedName("support_category")
    val support_category: String,      // 카테고리

    @SerializedName("bookmark_post")
    val bookmark_post: Int,                // 즐겨찾기 여부
)