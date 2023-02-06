package com.umc.badjang.utils

object Constants {
    const val TAG : String = "로그"
}

object API {
    const val BASE_URL : String = "https://prod.badjang2023.shop/"

    const val SEARCH_SCHOLARSHIP : String = "scholarships"

    const val SEARCH_SUPPORT : String = "supports"

    const val VIEWCOUNT_SCHOLARSHIP : String = "scholarships/{scholarshipIdx}"

    const val VIEWCOUNT_SUPPORT : String = "{supportIdx}"

    const val SUPPORT_OPI_BASE_URL : String = "https://www.youthcenter.go.kr/"

    const val OPI_SUPPORT : String = "opi/empList.do"

    const val OPI_SUPPORT_ID : String = "73444351051dbc5ea4541693"
}

enum class  RESPONSE_STATE {
    OKAY,
    FAIL
}