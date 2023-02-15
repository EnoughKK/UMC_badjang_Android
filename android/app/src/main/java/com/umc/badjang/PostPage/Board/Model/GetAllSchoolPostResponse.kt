package com.umc.badjang.PostPage.Board.Model

import com.umc.badjang.MyPage.Noti.model.GetNotiRes

data class GetAllSchoolPostResponse(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<GetAllSchoolPostRes>
)
