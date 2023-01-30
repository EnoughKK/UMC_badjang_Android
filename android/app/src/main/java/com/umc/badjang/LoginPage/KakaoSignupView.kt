package com.umc.badjang.LoginPage

import com.umc.badjang.LoginPage.models.KakaoSignupResponse

interface KakaoSignupView {
    fun onKakaoPostSignUpSuccess(response: KakaoSignupResponse)
    fun onKakaoPostSignUpFailure(message: String)
}