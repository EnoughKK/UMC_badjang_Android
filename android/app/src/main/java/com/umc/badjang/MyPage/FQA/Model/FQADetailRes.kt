package com.umc.badjang.MyPage.FQA.Model

data class FQADetailRes(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : FQADetailResponse
)
