package com.umc.badjang.Searchpage.models

import com.google.gson.annotations.SerializedName

data class AllSearchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MutableList<AllSearchResult> // 우리 학교 장학금 정보 list
)

data class AllSearchResult(
    @SerializedName("getSearchBoardRes") val getSearchBoardRes :  MutableList<GetSearchBoardRes>, //게시판 검색 리스트
    @SerializedName("getSearchScholarshipRes") val getSearchScholarshipRes : MutableList<GetSearchScholarshipRes>, //장학금 검색 리스트
    @SerializedName("getSearchSupportRes") val getSearchSupportRes :MutableList<GetSearchSupportRes> //지원금 검색리스트
)

//게시판 검색 리스트
data class GetSearchBoardRes (
    @SerializedName("post_idx") val post_idx: Long, //게시글 index
    @SerializedName("post_name") val post_name: String, //제목
    @SerializedName("post_content") val post_content: String,  //내용
    @SerializedName("post_image") val post_image: String?, //이미지
    @SerializedName("post_view") val post_view : Int, //조회수
    @SerializedName("post_recommend") val post_recommend : Int, //추천수
    @SerializedName("post_comment") val post_comment : Int, //댓글수
    @SerializedName("post_anonymity") val post_anonymity : String //익명성
)


//장학금
data class GetSearchScholarshipRes(
    @SerializedName("scholarship_idx") val scholarship_idx: Long, //장학금 인덱스
    @SerializedName("scholarship_name") val scholarship_name: String, //장학금 이름
    @SerializedName("scholarship_institution") val scholarship_institution: String?, //주관기관
    @SerializedName("scholarship_content") val scholarship_content: String?, //내용
    @SerializedName("scholarship_image") val scholarship_image: String?, //이미지
    @SerializedName("scholarship_homepage") val scholarship_homepage: String?, //홈페이지
    @SerializedName("scholarship_view") val scholarship_view: Int, //조회수
    @SerializedName("scholarship_comment") val scholarship_comment: Int, //댓글수
    @SerializedName("scholarship_scale") val scholarship_scale: String?, //장학금규모
    @SerializedName("scholarship_term") val scholarship_term: String?,//신청기간
    @SerializedName("scholarship_presentation") val scholarship_presentation: String, //심사발표
    @SerializedName("scholarship_createAt") val scholarship_createAt: String, //작성시간
    @SerializedName("scholarship_updateAt") val scholarship_updateAt: String, //수정시간
    @SerializedName("scholarship_status") val scholarship_status: String="Y", //상태
    @SerializedName("scholarship_univ") val scholarship_univ: String?=null, //학교
    @SerializedName("scholarship_college") val scholarship_college: String?=null, //단과대학
    @SerializedName("scholarship_department") val scholarship_department: String?=null, //학과
    @SerializedName("scholarship_grade") val scholarship_grade: String?=null,//학년
    @SerializedName("scholarship_semester") val scholarship_semester: String?=null,//학기
    @SerializedName("scholarship_province") val scholarship_province: String?=null,//도
    @SerializedName("scholarship_city") val scholarship_city: String?=null,// 시/군/구
    @SerializedName("scholarship_category") val scholarship_category: String, //카테고리
)

//지원금
data class GetSearchSupportRes(
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
