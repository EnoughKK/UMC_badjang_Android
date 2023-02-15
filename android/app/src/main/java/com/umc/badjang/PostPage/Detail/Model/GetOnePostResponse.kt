package com.umc.badjang.PostPage.Detail.Model

import com.umc.badjang.MyPage.Noti.model.GetNotiRes

data class GetOnePostResponse(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<GetOnePostRes>
)
