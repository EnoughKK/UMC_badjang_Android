package com.umc.badjang.PostPage.Detail.Model

data class PostCommentReq(
    val post_idx : Int,
    val user_idx : Int,
    val comment_content : String,
    val comment_anonymity : String,
    val comment_status : String
)




