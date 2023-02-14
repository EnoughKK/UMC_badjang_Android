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

    const val OPI_SUPPORT_KEY : String = "73444351051dbc5ea4541693"

}

object supportApiUrl {
    const val URL1 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=취업지원&openApiVlak=73444351051dbc5ea4541693"
    const val URL2 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=창업지원&openApiVlak=73444351051dbc5ea4541693"
    const val URL3 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=주거&openApiVlak=73444351051dbc5ea4541693"
    const val URL4 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=금융&openApiVlak=73444351051dbc5ea4541693"
    const val URL5 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=생활&openApiVlak=73444351051dbc5ea4541693"
    const val URL6 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=복지&openApiVlak=73444351051dbc5ea4541693"
    const val URL7 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=정책참여&openApiVlak=73444351051dbc5ea4541693"
    const val URL8 : String = "https://www.youthcenter.go.kr/opi/empList.do?pageIndex=1&display=30&query=코로나19&openApiVlak=73444351051dbc5ea4541693"
}

enum class  RESPONSE_STATE {
    OKAY,
    FAIL
}