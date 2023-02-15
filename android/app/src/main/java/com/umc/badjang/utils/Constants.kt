package com.umc.badjang.utils

object Constants {
    const val TAG : String = "로그"
}

object API {
    const val BASE_URL : String = "https://prod.badjang2023.shop/"

    const val SEARCH_SCHOLARSHIP : String = "scholarships"

    const val VIEWCOUNT_SCHOLARSHIP : String = "scholarships/{scholarshipIdx}"

    const val SCHOLARSHIP_COMMENTS: String = "scholarships/comment"

    const val NEW_COMMENTS: String = "scholarships/comment/new-comment"

    const val DELETE_COMMENTS: String = "scholarships/comment/delete/{scholarship_comment_idx}"

    const val SCHOLARSHIP_FILTER: String = "scholarships/myfilter"

    const val EDIT_COMMENTS: String = "scholarships/comment/modify/{scholarship_comment_idx}"

    const val SCHOLARSHIP_BOOKMARK: String = "scholarships/{scholarshipIdx}/bookmark_check"

    const val BOOKMARK_EDIT: String = "scholarships/{scholarshipIdx}/bookmark"

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
    FAIL,
    NO_CONTENT
}