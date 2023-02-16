package com.umc.badjang.SearchMorePage

import android.graphics.Bitmap

data class SearchMorePostData(
    val searchPostProfileImg: Bitmap, // 프로필 이미지
    val searchPostNickname: String,   // 닉네임
    val searchPostDate: String,       // 작성일
    val searchPostTitle: String,      // 게시글 제목
    val searchPostContent: String,    // 게시글 내용
    val searchPostImg: Bitmap?,        // 게시글 이미지
    val searchPostCommentsCnt: Int,   // 댓글수
    val searchPostViewCnt: Int,       // 조회수
    val searchPostGoodCnt: Int        // 좋아요수
)
