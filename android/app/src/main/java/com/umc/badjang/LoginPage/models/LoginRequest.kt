package com.umc.badjang.LoginPage.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_email") val user_email: String,
    @SerializedName("user_password") val user_password: String
)
