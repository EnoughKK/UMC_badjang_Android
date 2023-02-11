package com.umc.badjang.MyPage.MyWrite.model

data class MyWriteChatResponse(
    val comment_idx: Int,
    val post_idx: Int,
    val post_name:String,
    val post_category: String,
    val comment_content: String
)
