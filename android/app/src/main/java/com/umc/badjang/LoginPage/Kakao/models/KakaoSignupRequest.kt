package com.umc.badjang.LoginPage.Kakao.models

import com.google.gson.annotations.SerializedName

data class KakaoSignupRequest (
    @SerializedName("access_token") val access_token: String,
)
