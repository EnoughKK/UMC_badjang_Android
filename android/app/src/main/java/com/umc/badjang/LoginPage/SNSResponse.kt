package com.umc.badjang.LoginPage

import com.google.gson.annotations.SerializedName

data class SNSResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : Result
)

data class Result(
    @SerializedName("user_idx") val user_idx : Int,
    @SerializedName("jwt") val jwt : String
)
