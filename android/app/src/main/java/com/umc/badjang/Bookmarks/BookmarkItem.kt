package com.umc.badjang.Bookmarks

import android.graphics.Bitmap

// 즐겨찾기 아이템 데이터
interface BookmarkItem

// 장학금, 지원금 데이터
data class BookmarkScholarshipData (
    val bookmarkScholarshipInstitution: String?, // 장학금, 지원금 기관명
    val bookmarkScholarshipImg: Bitmap?,         // 장학금, 지원금 게시물 이미지
    val bookmarkScholarshipTitle: String,       // 장학금, 지원금 게시물 제목
    val bookmarkScholarshipContent: String?,     // 장학금, 지원금 게시물 내용
    val bookmarkScholarshipCommentsCnt: Int,    // 댓글수
    val bookmarkScholarshipViewCnt: Int,        // 조회수
): BookmarkItem

data class BookmarkScholarshipDataString (
    val bookmarkScholarshipInstitution: String?, // 장학금, 지원금 기관명
    val bookmarkScholarshipImg: String?,         // 장학금, 지원금 게시물 이미지
    val bookmarkScholarshipTitle: String,       // 장학금, 지원금 게시물 제목
    val bookmarkScholarshipContent: String?,     // 장학금, 지원금 게시물 내용
    val bookmarkScholarshipCommentsCnt: Int,    // 댓글수
    val bookmarkScholarshipViewCnt: Int,        // 조회수
): BookmarkItem

// 게시글 데이터
data class BookmarkPostData(
    val bookmarkPostProfileImg: Bitmap, // 프로필 이미지
    val bookmarkPostNickname: String,   // 닉네임
    val bookmarkPostDate: String,       // 작성일
    val bookmarkPostTitle: String,      // 인기글 제목
    val bookmarkPostContent: String,    // 인기글 내용
    val bookmarkPostImg: Bitmap?,        // 인기글 이미지
    val bookmarkPostCommentsCnt: Int,   // 댓글수
    val bookmarkPostViewCnt: Int,       // 조회수
    val bookmarkPostGoodCnt: Int        // 좋아요수
): BookmarkItem

data class BookmarkPostDataString(
    val bookmarkPostProfileImg: Bitmap, // 프로필 이미지
    val bookmarkPostNickname: String,   // 닉네임
    val bookmarkPostDate: String,       // 작성일
    val bookmarkPostTitle: String,      // 인기글 제목
    val bookmarkPostContent: String,    // 인기글 내용
    val bookmarkPostImg: String?,        // 인기글 이미지
    val bookmarkPostCommentsCnt: Int,   // 댓글수
    val bookmarkPostViewCnt: Int,       // 조회수
    val bookmarkPostGoodCnt: Int        // 좋아요수
): BookmarkItem