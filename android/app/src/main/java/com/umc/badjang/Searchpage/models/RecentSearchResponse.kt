package com.umc.badjang.Searchpage.models

import com.google.gson.annotations.SerializedName

data class RecentSearchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean, //요청 성공 여부
    @SerializedName("code") val code: Int, //응답코드
    @SerializedName("message") val message: String, //응답 메시지
    @SerializedName("result") val result: Result //결과
)
data class Result(
    @SerializedName("search_history_idx") val search_history_idx:Long, //최근 검색어 idx
    @SerializedName("user_idx") val user_idx: Long, //사용자 idx
    @SerializedName("search_history_query") val search_history_query: Long //최근 검색어 내용
)
