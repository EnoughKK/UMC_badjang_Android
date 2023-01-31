package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap

// 전국소식 리스트 데이터
data class NationalNewsData (
    val nationalNewsInstitution: String, // 전국소식 기관명
    val nationalNewsImg: Bitmap,         // 전국소식 게시물 이미지
    val nationalNewsTitle: String,       // 전국소식 게시물 제목
    val nationalNewsContent: String,     // 전국소식 게시물 내용
    val nationalNewsCommentsCnt: Int,    // 댓글수
    val nationalNewsViewCnt: Int,        // 조회수
)