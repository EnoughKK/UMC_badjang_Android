package com.umc.badjang.MyPage.Model

data class MyProfileResponse(
    val user_idx : Int,
    val user_email : String,
    val user_name : String,
    val user_profileimage_url : String?,
    val user_type : String,
    val user_birth : String,
    val user_phone : String,
    val user_push_yn : String,
    val user_on_off : String,
    val user_univ : String?,
    val user_college : String?,
    val user_department : String?,
    val user_grade : String?,
    val user_semester : String?,
    val user_province : String?,
    val user_city : String?
)
