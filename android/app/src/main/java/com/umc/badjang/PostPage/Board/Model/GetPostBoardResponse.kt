package com.umc.badjang.PostPage.Board.Model

import com.umc.badjang.MyPage.Noti.model.GetNotiRes

data class GetPostBoardResponse(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<GetPostBoardRes>
)
