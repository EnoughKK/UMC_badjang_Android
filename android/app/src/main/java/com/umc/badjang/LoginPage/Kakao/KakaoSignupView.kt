package com.umc.badjang.LoginPage.Kakao

import com.umc.badjang.LoginPage.Kakao.models.KakaoSignupResponse

interface KakaoSignupView {
    fun onKakaoPostSignUpSuccess(response: KakaoSignupResponse)
    fun onKakaoPostSignUpFailure(message: String)
}