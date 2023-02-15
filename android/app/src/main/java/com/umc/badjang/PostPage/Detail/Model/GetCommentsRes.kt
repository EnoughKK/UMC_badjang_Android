package com.umc.badjang.PostPage.Detail.Model

data class GetCommentsRes(
    val comment_idx : Int,
    val user_idx : Int,
    val post_idx : Int,
    val comment_content : String,
    val comment_recommend : Int,
    val comment_anonymity : String,
    val comment_createAt : String,
    val comment_updatedAt : String,
    val comment_status : String,
    val user_name : String?,
    val user_profileimage_url : String,
    val recommend_status : Int
)




