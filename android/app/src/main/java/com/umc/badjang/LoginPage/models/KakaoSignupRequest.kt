package com.umc.badjang.LoginPage.models

import com.google.gson.annotations.SerializedName

data class KakaoSignupRequest (
    @SerializedName("accessToken") val accessToken: String,
)
