package com.umc.badjang.MyPage.Model

data class MyProfileRes(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : MyProfileResponse
)
