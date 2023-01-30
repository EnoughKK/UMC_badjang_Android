package com.umc.badjang.utils

object Constants {
    const val TAG : String = "로그"
}

object API {
    const val BASE_URL : String = "https://prod.badjang2023.shop/"

    const val SEARCH_SCHOLARSHIP : String = "scholarships"

    const val SEARCH_SUPPORT : String = "supports"

    const val VIEWCOUNT_SCHOLARSHIP : String = "{scholarshipIdx}"
}

enum class  RESPONSE_STATE {
    OKAY,
    FAIL
}