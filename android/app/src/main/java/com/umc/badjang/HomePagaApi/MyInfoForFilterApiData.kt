package com.umc.badjang.HomePagaApi

import com.google.gson.annotations.SerializedName

data class MyInfoForFilterApiData(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,  // 요청 성공 여부

    @SerializedName("code")
    val code: Int,           // 응답 코드

    @SerializedName("message")
    val message: String,     // 응답 메시지

    @SerializedName("result")
    val result: MyInfoForFilterApiResult
)

data class MyInfoForFilterApiResult(
    @SerializedName("user_idx")
    val user_idx : Int,

    @SerializedName("user_email")
    val user_email : String,

    @SerializedName("user_name")
    val user_name : String,

    @SerializedName("user_profileimage_url")
    val user_profileimage_url : String?,

    @SerializedName("user_type")
    val user_type : String,

    @SerializedName("user_birth")
    val user_birth : String,

    @SerializedName("user_phone")
    val user_phone : String,

    @SerializedName("user_push_yn")
    val user_push_yn : String,

    @SerializedName("user_on_off")
    val user_on_off : String,

    @SerializedName("user_univ")
    val user_univ : String?,

    @SerializedName("user_college")
    val user_college : String?,

    @SerializedName("user_department")
    val user_department : String?,

    @SerializedName("user_grade")
    val user_grade : String?,

    @SerializedName("user_semester")
    val user_semester : String?,

    @SerializedName("user_province")
    val user_province : String?,

    @SerializedName("user_city")
    val user_city : String?
)