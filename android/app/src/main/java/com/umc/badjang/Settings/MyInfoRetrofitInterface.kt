package com.umc.badjang.Settings

import com.umc.badjang.Settings.model.PostNameModifyReq
import com.umc.badjang.Settings.model.PostNameModifyResponse
import com.umc.badjang.Settings.model.PostPhoneNumberModifyReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MyInfoRetrofitInterface {
    @POST("/users/modify")
    fun postNameModify(@Body params: PostNameModifyReq): Call<PostNameModifyResponse>

    @POST("/users/modify")
    fun postPhoneNumberModify(@Body params: PostPhoneNumberModifyReq): Call<PostNameModifyResponse>
}