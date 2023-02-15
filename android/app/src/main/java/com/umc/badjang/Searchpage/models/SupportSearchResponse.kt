package com.umc.badjang.Searchpage.models

import com.google.gson.annotations.SerializedName

data class SupportSearchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MutableList<SupportSearchResult>
)

data class SupportSearchResult(
    @SerializedName("support_idx") val support_idx: Long, //지원금 index
    @SerializedName("support_policy") val support_policy: String, //장학금 이름
    @SerializedName("support_name") val support_name: String,//지원금 이름
    @SerializedName("support_institution") val support_institution: String?, //주관기관
    @SerializedName("support_content") val support_content: String?, //내용
    @SerializedName("support_image") val support_image: String?, //이미지
    @SerializedName("support_homepage") val support_homepage: String?, //홈페이지
    @SerializedName("support_view") val support_view: Int, //조회수
    @SerializedName("support_comment") val support_comment: Int, //댓글수
    @SerializedName("support_scale") val support_scale: String?, //장학금규모
    @SerializedName("support_term") val support_term: String?, //신청기간
    @SerializedName("support_presentation") val support_presentation: String? // 심사발표
)
