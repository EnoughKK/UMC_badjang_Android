package com.umc.badjang.LoginPage

import com.umc.badjang.LoginPage.models.KakaoSignupRequest
import com.umc.badjang.LoginPage.models.KakaoSignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoRetrofitInterface {
    @POST("/oauth/kakao")
    fun postKakaoSignup(@Body params: KakaoSignupRequest) : Call<KakaoSignupResponse>
}