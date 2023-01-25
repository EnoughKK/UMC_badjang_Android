package com.umc.badjang.LoginPage.models

import com.google.gson.annotations.SerializedName

data class ResultLogin( @SerializedName("user_idx") val user_idx : Int,
                       @SerializedName("jwt") val jwt : String)

data class LoginResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : ResultLogin?
)
