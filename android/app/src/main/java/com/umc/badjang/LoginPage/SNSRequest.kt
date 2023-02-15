package com.umc.badjang.LoginPage

import com.google.gson.annotations.SerializedName

data class SNSRequest(
    @SerializedName("user_idx") val user_idx:Int,
    @SerializedName("user_name") val user_name: String,
    @SerializedName("user_birth") val user_birth: String,
    @SerializedName("user_phone") val user_phone: String,
    @SerializedName("user_push_yn") val user_push_yn: String
)
