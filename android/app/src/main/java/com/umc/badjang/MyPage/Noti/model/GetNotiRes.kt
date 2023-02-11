package com.umc.badjang.MyPage.Noti.model

data class GetNotiRes (
    val notice_idx : Int,
    val notice_title : String,
    val notice_content : String,
    val notice_image : String?,
    val notice_createAt : String,
    val notice_updateAt : String?
)