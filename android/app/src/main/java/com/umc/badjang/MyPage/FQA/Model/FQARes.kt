package com.umc.badjang.MyPage.FQA.Model

data class FQARes(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<FQAResponse>
)
