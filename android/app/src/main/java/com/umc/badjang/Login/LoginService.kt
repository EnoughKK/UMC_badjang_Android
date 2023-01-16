package com.umc.badjang.Login

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("/app_login/")
    fun requestLogin(
        @Field("userid") userid:String,
        @Field("userpw") userpw:String
    ): Call<Login>
}