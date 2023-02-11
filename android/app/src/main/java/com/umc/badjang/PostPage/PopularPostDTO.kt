package com.umc.badjang.PostPage

import android.graphics.Bitmap

class PopularPostDTO (
    val userImage: Bitmap,
    val postWriter: String,
    val postDate: String,
    val postCategory: String,
    val postTitle: String,
    val postContents: String,
    val postGoodCounts: Int,
    val postViewCounts: Int
    )

