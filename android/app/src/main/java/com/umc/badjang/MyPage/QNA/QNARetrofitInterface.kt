package com.umc.badjang.MyPage.QNA

import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.Model.PostProfileModifyReq
import com.umc.badjang.MyPage.Model.PostUserInfoModify
import com.umc.badjang.MyPage.MyWrite.model.MyWriteChatRes
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QNARetrofitInterface {
    @GET("/mypage/myboard")
    fun getMyWrite(): Call<MyWriteRes>

    @GET("/mypage/mycomment")
    fun getMyWriteChat(): Call<MyWriteChatRes>

}