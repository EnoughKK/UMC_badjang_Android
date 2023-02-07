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
    val result: MainNationalNewsApiResult, // 전국소식 정보

    val result: MutableList<MainNationalNewsApiResult>, // 전국소식 정보 list

)

data class MainNationalNewsApiResult(
    @SerializedName("total_idx")
    val total_idx: Int,          // 전국소식 인덱스

    @SerializedName("scholarship_idx")
    val scholarship_idx: Int,   // 장학금 인덱스

    @SerializedName("fund_idx")
    val fund_idx: Int,          // 지원금 인덱스

    @SerializedName("total_createAt")
    val total_createAt: String, // 생성 일시

    @SerializedName("total_updateAt")
    val total_updateAt: String, // 변경 일시

    @SerializedName("total_status")
    val total_status: String,   // 잔존여부
)