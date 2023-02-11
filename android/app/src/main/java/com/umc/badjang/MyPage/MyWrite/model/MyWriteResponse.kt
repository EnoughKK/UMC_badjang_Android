package com.umc.badjang.MyPage.MyWrite.model

data class MyWriteResponse(
    val user_name : String,
    val user_profileimage_url : String,
    val post_createAt:String,
    val post_idx: Int,
    val post_name: String,
    val post_content: String,
    val post_image: String?,
    val post_view: Int,
    val post_recommend: Int,
    val post_comment: Int,
    val post_category: String,
    val post_anonymity: String
)
