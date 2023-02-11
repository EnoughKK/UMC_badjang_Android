package com.umc.badjang.HomePage

// 학교 정보
data class UniversitySelectData(
    val results: MutableList<UniversityResults>
)

data class UniversityResults(
    val university_name: String, // 학교 이름
    val university_info: MutableList<UniversityInfo>
)

data class UniversityInfo(
    val college_name: String, // 단과대학
    val university_department: MutableList<String> // 학과
)
