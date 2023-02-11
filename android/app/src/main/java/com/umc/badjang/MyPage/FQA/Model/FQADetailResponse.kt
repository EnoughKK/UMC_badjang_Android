package com.umc.badjang.MyPage.FQA.Model

data class FQADetailResponse(
    val faq_title : String,
    val faq_content : String,
    val faq_idx : Long,
    val faq_image : String?,
    val faq_createAt : String,
    val faq_updateAt : String,
    val faq_status : String
)
