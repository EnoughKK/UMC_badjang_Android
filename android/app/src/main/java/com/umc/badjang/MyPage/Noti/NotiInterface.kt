package com.umc.badjang.MyPage.Noti

import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.Model.PostProfileModifyReq
import com.umc.badjang.MyPage.Model.PostUserInfoModify
import com.umc.badjang.MyPage.MyWrite.model.MyWriteChatRes
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import com.umc.badjang.MyPage.Noti.model.GetDetailNotiResponse
import com.umc.badjang.MyPage.Noti.model.GetNotiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotiInterface {
    @GET("/mypage/notice")
    fun getNoti(): Call<GetNotiResponse>

    @GET("/mypage/notice/{notice_idx}")
    fun getDetailNoti(@Path("notice_idx") notice_idx : Int): Call<GetDetailNotiResponse>

}