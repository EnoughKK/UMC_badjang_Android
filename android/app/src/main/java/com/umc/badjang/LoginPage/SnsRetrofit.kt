package com.umc.badjang.LoginPage

import com.umc.badjang.Settings.Logout.models.LogoutRequest
import com.umc.badjang.Settings.Logout.models.LogoutResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SnsRetrofit {
    @POST("/users/sns")
    fun requestSNS(@Header("X-ACCESS-TOKEN")jwt: String, @Body params: SNSRequest): Call<SNSResponse>
}