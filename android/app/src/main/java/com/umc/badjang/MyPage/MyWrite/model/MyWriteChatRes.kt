package com.umc.badjang.MyPage.MyWrite.model

data class MyWriteChatRes(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<MyWriteChatResponse>
)
