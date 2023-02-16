package com.umc.badjang.HomeMorePage

import android.graphics.Bitmap

// 전국소식 리스트 데이터
data class NationalNewsData (
    val scholarshipIdx: Int?,              // 장학금 idx
    val supportIdx: Int?,                  // 지원금 idx
    val nationalNewsInstitution: String?,  // 전국소식 기관명
    val nationalNewsTitle: String,         // 전국소식 게시물 제목 = 장학금/지원금 이름
    val nationalNewsContent: String?,      // 전국소식 게시물 내용
    val nationalNewsImg: String?,          // 전국소식 게시물 이미지
    val nationalNewsCommentsCnt: Int,      // 댓글수
    val nationalNewsViewCnt: Int,          // 조회수
    var bookmarkCheck: Boolean
)

data class NationalNewsDataBitmap (
    val scholarshipIdx: Int?,              // 장학금 idx
    val supportIdx: Int?,                  // 지원금 idx
    val nationalNewsInstitution: String?,  // 전국소식 기관명
    val nationalNewsTitle: String,         // 전국소식 게시물 제목 = 장학금/지원금 이름
    val nationalNewsContent: String?,      // 전국소식 게시물 내용
    val nationalNewsImg: Bitmap?,          // 전국소식 게시물 이미지
    val nationalNewsCommentsCnt: Int,      // 댓글수
    val nationalNewsViewCnt: Int,          // 조회수
    var bookmarkCheck: Boolean
)