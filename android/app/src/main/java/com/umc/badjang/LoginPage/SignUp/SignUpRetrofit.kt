package com.umc.badjang.LoginPage.SignUp

import com.umc.badjang.LoginPage.SignUp.models.SignUpRequest
import com.umc.badjang.LoginPage.models.SignUpResponse
import retrofit2.Call
import retrofit2.http.*

interface SignUpRetrofit {
    @POST("/users") //회원가입
    fun requestSignup(@Body params: SignUpRequest): Call<SignUpResponse>
}
