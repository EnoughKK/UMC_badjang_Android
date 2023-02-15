package com.umc.badjang.PostPage.Board.Model

import com.umc.badjang.MyPage.Noti.model.GetNotiRes

data class GetAllSchoolPostRes(
    val post_idx : Int,
    val user_idx : Int,
    val user_name : String,
    val user_profileimage_url : String,
    val post_name : String,
    val post_content : String,
    val post_image : String?,
    val post_view : Int,
    val post_recommend : Int,
    val post_comment : Int,
    val post_anonymity : String,
    val post_category:String,
    val post_school_name : String,
    val post_createAt : String,
    val recommend_check : Int
)
