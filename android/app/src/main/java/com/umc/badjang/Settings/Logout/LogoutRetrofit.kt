package com.umc.badjang.Settings.Logout

import com.umc.badjang.Settings.Logout.models.LogoutRequest
import com.umc.badjang.Settings.Logout.models.LogoutResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LogoutRetrofit {

    @POST("/users/logout") //로그아웃
    fun requestLogout(@Header("X-ACCESS-TOKEN")jwt: String, @Body params: LogoutRequest): Call<LogoutResponse>
}