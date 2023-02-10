package com.umc.badjang.Settings.Myinfo


import com.umc.badjang.Settings.Myinfo.models.MyinfoRequest
import com.umc.badjang.Settings.Myinfo.models.MyinfoResponse
import com.umc.badjang.Settings.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface MyinfoRetrofit {

    @POST("/users/modify") //회원 정보 수정
    fun requestMyinfo(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Body params: MyinfoRequest
    ): Call<MyinfoResponse>

    @GET("/mypage") //회원정보 받기
    fun requestUserinfo(@Header("X-ACCESS-TOKEN")jwt: String): Call<UserResponse>
}

