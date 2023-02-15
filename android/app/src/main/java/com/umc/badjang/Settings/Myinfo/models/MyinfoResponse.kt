package com.umc.badjang.Settings.Myinfo.models

import com.google.gson.annotations.SerializedName

data class MyinfoResponse(
    @SerializedName("result") val result : String,
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String
)
