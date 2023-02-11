package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap

// 인기글 리스트 데이터
data class PopularPostData(
    val popularPostProfileImg: Bitmap, // 프로필 이미지
    val popularPostNickname: String,   // 닉네임
    val popularPostDate: String,       // 작성일
    val popularPostTitle: String,      // 인기글 제목
    val popularPostContent: String,    // 인기글 내용
    val popularPostImg: Bitmap?,        // 인기글 이미지
    val popularPostCommentsCnt: Int,   // 댓글수
    val popularPostViewCnt: Int,       // 조회수
    val popularPostGoodCnt: Int        // 좋아요수
)
