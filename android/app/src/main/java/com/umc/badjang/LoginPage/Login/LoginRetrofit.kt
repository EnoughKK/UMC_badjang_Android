package com.umc.badjang.LoginPage.Login

import com.umc.badjang.LoginPage.Login.models.LoginRequest
import com.umc.badjang.LoginPage.Login.models.LoginResponse
import com.umc.badjang.LoginPage.SignUp.models.SignUpRequest
import com.umc.badjang.LoginPage.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofit {

    @POST("/users/logIn") //로그인
   fun requestLogin(@Body params: LoginRequest): Call<LoginResponse>
}