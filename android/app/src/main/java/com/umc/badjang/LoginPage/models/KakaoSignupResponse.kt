package com.umc.badjang.LoginPage.models

import com.google.gson.annotations.SerializedName

data class KakaoSignupResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val result: Result
)
data class Result(
    @SerializedName("userId") val userId: Int,
    @SerializedName("jwt") val jwt: String
)
