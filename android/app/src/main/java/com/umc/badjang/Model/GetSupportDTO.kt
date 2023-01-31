package com.umc.badjang.Model

// 지원금 조회 (필터사용) DTO
class GetSupportDTO (
    var support_idx: Long?,               // 장학금 idx
    var support_name: String?,            // 장학금 이름
    var support_institution: String?,     // 주관기관
    var support_content: String?,         // 내용
    var support_image: String?,           // 이미지
    var support_homepage: String?,        // 홈페이지
    var support_view: Int?,               // 조회수
    var support_comment: Int?,            // 댓글수
    var support_scale: String?,           // 장학금규모
    var support_term: String?,            // 신청기간
    var support_presentation: String?,    // 심사발표
    var support_univ: String?,            // 학교
    var support_category: String?         // 카테고리
)