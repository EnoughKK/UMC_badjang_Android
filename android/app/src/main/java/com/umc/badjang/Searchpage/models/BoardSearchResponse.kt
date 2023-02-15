package com.umc.badjang.Searchpage.models

import com.google.gson.annotations.SerializedName

data class BoardSearchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MutableList<BoardSearchResult>
)

//게시판 검색 리스트
data class BoardSearchResult (
    @SerializedName("post_idx") val post_idx: Long, //게시글 index
    @SerializedName("post_name") val post_name: String, //제목
    @SerializedName("post_content") val post_content: String,  //내용
    @SerializedName("post_image") val post_image: String?, //이미지
    @SerializedName("post_view") val post_view : Int, //조회수
    @SerializedName("post_recommend") val post_recommend : Int, //추천수
    @SerializedName("post_comment") val post_comment : Int, //댓글수
    @SerializedName("post_anonymity") val post_anonymity : String //익명성
)
