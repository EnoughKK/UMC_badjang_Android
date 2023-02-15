package com.umc.badjang.PostPage.Detail.Model

import com.umc.badjang.MyPage.Noti.model.GetNotiRes

data class GetCommentsResponse(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<GetCommentsRes>
)
