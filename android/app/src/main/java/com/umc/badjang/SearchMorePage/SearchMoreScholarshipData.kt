package com.umc.badjang.SearchMorePage

data class SearchMoreScholarshipData(
    var scholarship_idx: Long?,               // 장학금 idx
    var scholarship_name: String?,            // 장학금 이름
    var scholarship_institution: String?,     // 주관기관
    var scholarship_content: String?,         // 내용
    var scholarship_image: String?,           // 이미지
    var scholarship_homepage: String?,        // 홈페이지
    var scholarship_view: Int?,               // 조회수
    var scholarship_comment: Int?,            // 댓글수
    var scholarship_scale: String?,           // 장학금규모
    var scholarship_term: String?,            // 신청기간
    var scholarship_presentation: String?,    // 심사발표
    var scholarship_univ: String?,            // 학교
    var scholarship_category: String?         // 카테고리
)
