package com.umc.badjang.HomePagaApi

import com.google.gson.annotations.SerializedName

// 학교, 지역 정보를 전달하고 받을 값
data class UniversityFilterResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 요청 성공 여부

    @SerializedName("code")
    val code: Int,           // 응답 코드

    @SerializedName("message")
    val message: String,     // 응답 메시지

    @SerializedName("result")
    val result: String
)

// 서버에 저장할 학교, 지역 정보
data class UniversityFilterAddData(
    @SerializedName("user_idx")
    val user_idx: Int,             // 유저 번호

    @SerializedName("user_univ")
    val user_univ: String? = null,        // 대학교

    @SerializedName("user_college")
    val user_college: String? = null,     // 단과대학

    @SerializedName("user_department")
    val user_department: String? = null,  // 학과

    @SerializedName("user_grade")
    val user_grade: String? = null,       // 학년

    @SerializedName("user_semester")
    val user_semester: String? = null,    // 학기

    @SerializedName("user_province")
    val user_province: String? = null,    // 도

    @SerializedName("user_city")
    val user_city: String? = null,        // 시군구
)