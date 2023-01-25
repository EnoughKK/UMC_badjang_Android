package com.umc.badjang.LoginPage.models

data class KakaoOauthRequest(
    val authCode: String,
    val oauthClientName: String = "KAKAO"
)
