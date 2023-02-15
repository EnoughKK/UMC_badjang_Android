package com.umc.badjang.PostPage.AllBoard.Model

data class GetAllPostBoardResponse(
    val isSuccess : String,
    val code : Int,
    val message : String,
    val result : List<GetAllPostBoardRes>
)
