package com.umc.badjang.HomePagaApi

import com.google.gson.annotations.SerializedName

data class MainMySchoolApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 성공여부

    @SerializedName("code")
    val code: Int,           // 코드

    @SerializedName("message")
    val message: String,     // 메시지

    @SerializedName("result")
    val result: MutableList<MainMySchoolApiResult>, // 우리 학교 장학금 정보 list
)

data class MainMySchoolApiResult(
    @SerializedName("scholarship_idx")
    val scholarship_idx: Int,               // 장학금 인덱스

    @SerializedName("user_idx")
    val user_idx: Int,                      // 사용자 인덱스

    @SerializedName("scholarship_createAt")
    val scholarship_createAt: String,       // 생성 시간

    @SerializedName("scholarship_updateAt")
    val scholarship_updateAt: String,       // 수정 시간

    @SerializedName("scholarship_status")
    val scholarship_status: String,         // 장학금 여부

)