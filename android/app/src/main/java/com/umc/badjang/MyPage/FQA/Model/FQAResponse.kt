package com.umc.badjang.MyPage.FQA.Model

data class FQAResponse(
    val faq_content: String,
    val faq_title: String,
    val faq_img: String,
    val faq_idx: Long,
    val faq_status: String,
    val faq_updateAt: String,
    val faq_createAt: String
)
