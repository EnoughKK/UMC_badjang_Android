package com.umc.badjang.PostPage.Detail.Model

data class GetOnePostRes(
    val post_idx : Int,
    val user_idx : Int,
    val post_category : String,
    val post_name : String,
    val post_content : String,
    val post_image : String?,
    val post_view : Int,
    val post_recommend : Int,
    val post_comment : Int,
    val post_createAt : String,
    val post_updateAt : String?,
    val post_status : String,
    val post_anonymity : String,
    val school_name_idx : Int,
    val post_bookmark : Int,
    val recommend_status : Int,
    val user_name : String?,
    val user_profileimage_url : String,
    val bookmark_count : Int
)




