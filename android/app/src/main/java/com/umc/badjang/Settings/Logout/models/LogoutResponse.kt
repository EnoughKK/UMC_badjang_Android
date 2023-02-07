package com.umc.badjang.Settings.Logout.models

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("result") val result : String,
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String
)
