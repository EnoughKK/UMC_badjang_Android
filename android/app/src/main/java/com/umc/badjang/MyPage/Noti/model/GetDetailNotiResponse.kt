package com.umc.badjang.MyPage.Noti.model

import com.umc.badjang.MyPage.MyWrite.model.MyWriteChatResponse

data class GetDetailNotiResponse(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : GetNotiRes
)
