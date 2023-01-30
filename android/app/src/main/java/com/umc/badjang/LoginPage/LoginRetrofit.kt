package com.umc.badjang.LoginPage

import com.umc.badjang.LoginPage.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginRetrofit {

    @POST("/users/logIn") //로그인
   fun requestLogin(@Body params: LoginRequest): Call<LoginResponse>

    @POST("/users") //회원가입
    fun requestSignup(@Body params: SignUpRequest): Call<SignUpResponse>

}