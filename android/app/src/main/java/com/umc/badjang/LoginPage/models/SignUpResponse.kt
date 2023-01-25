package com.umc.badjang.LoginPage.models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : ResultSignUp?
)

data class ResultSignUp(
    @SerializedName("user_idx") val user_idx : Int,
    @SerializedName("jwt") val jwt : String
)

