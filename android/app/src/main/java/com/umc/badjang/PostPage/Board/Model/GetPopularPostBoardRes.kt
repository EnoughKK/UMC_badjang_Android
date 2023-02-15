package com.umc.badjang.PostPage.Board.Model

data class GetPopularPostBoardRes(
    val popular_idx : Int,
    val post_idx : Int,
    val user_idx : Int,
    val school_name_idx : Int,
    val post_content: String,
    val post_createAt : String,
    val post_updateAt : String,
    val postp_status : String,
    val count : Int,
    val user_name : String?,
    val post_category : String,
    val post_anonymity : String,
    val user_profileimage_url : String,
    val post_image : String?,
    val post_view : Int,
    val post_recommend : Int,
    val post_name : String,
    val post_comment : Int
)




