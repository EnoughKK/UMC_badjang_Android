package com.umc.badjang.LoginPage.Kakao.models

import com.google.gson.annotations.SerializedName

data class KakaoSignupResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val result: Result
)
data class Result(
    @SerializedName("user_idx") val user_idx: Int,
    @SerializedName("jwt") val jwt: String
)
