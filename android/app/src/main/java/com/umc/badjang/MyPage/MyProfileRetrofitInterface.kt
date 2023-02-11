package com.umc.badjang.MyPage

import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.Model.PostProfileModifyReq
import com.umc.badjang.MyPage.Model.PostUserInfoModify
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyProfileRetrofitInterface {
    @GET("/mypage")
    fun getMyProfileRes(): Call<MyProfileRes>

    @POST("/users/modify")
    fun postProfileModify(@Body params: PostProfileModifyReq): Call<PostUserInfoModify>
}