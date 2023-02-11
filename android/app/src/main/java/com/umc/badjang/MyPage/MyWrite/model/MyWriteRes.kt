package com.umc.badjang.MyPage.MyWrite.model

data class MyWriteRes(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<MyWriteResponse>
)
