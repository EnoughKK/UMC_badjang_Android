package com.umc.badjang.HomePage

// 메인 페이지 인기글 리스트
data class MainPopularData(
    val popularNum: Int,
    val popularTitle: String,
    val popularGoodNum: Int,
    val popularViewNum: Int
)
