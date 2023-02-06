package com.umc.badjang.HomePage

import android.graphics.Bitmap

// 메인 페이지에 보이는 전국 소식 게시글 리스트

data class MainNationalNewsData(
    val nationalNewsImage: Bitmap,
    val nationalNewsTitle: String
)
