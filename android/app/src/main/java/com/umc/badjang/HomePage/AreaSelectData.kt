package com.umc.badjang.HomePage

// 지역 정보
data class AreaSelectData(
    val results: MutableList<AreaResults>
)

data class AreaResults(
    val province_name: String,          // 도 이름
    val city_name: MutableList<String>, // 시군구 이름
)