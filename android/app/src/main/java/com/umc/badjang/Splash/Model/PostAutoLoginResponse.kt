package com.umc.badjang.Splash.Model

data class PostAutoLoginResponse(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : PostAutoLoginRes
)
