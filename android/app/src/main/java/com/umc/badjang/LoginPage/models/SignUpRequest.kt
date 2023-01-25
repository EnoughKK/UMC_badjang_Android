package com.umc.badjang.LoginPage.models

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("user_email") val user_email: String,
    @SerializedName("user_password") val user_password: String,
    @SerializedName("user_name") val user_name: String,
    @SerializedName("user_birth") val user_birth: String,
    @SerializedName("user_phone") val user_phone: String,
    @SerializedName("user_type") val user_type: String,
    @SerializedName("user_push_yn") val user_push_yn: String
)
