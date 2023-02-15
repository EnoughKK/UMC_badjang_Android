package com.umc.badjang.Splash

import com.umc.badjang.Settings.model.PostNameModifyReq
import com.umc.badjang.Settings.model.PostNameModifyResponse
import com.umc.badjang.Settings.model.PostPhoneNumberModifyReq
import com.umc.badjang.Splash.Model.PostAutoLoginReq
import com.umc.badjang.Splash.Model.PostAutoLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SplashRetrofitInterface {
    @POST("/users/autologin")
    fun postAutoLogin(@Body params: PostAutoLoginReq): Call<PostAutoLoginResponse>

}