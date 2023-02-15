package com.umc.badjang.Retrofit

import com.google.gson.JsonElement
import com.umc.badjang.ScholarshipPage.Model.DeleteCommentsDTO
import com.umc.badjang.ScholarshipPage.Model.EditCommentsDTO
import com.umc.badjang.ScholarshipPage.Model.NewCommentsDTO
import com.umc.badjang.ScholarshipPage.Model.ScholarshipFilterDTO
import com.umc.badjang.utils.API
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// 장학금 조회 (필터사용) 인터페이스
interface ISearchScholarship {

    @GET(API.SEARCH_SCHOLARSHIP)
    fun searchScolarhip(@Query("category") category: String?, @Query("filter") filter: String?, @Query("order") order: String?) : Call<JsonElement>
}

// 장학금인덱스로 장학금조회(조회수1증가) 인터페이스
interface ISholarshipViewCount{

    @GET(API.VIEWCOUNT_SCHOLARSHIP)
    fun searchScholarshipIDx(@Path("scholarshipIdx") scholarshipIdx: Long?) : Call<JsonElement>
}

interface IScholarshipComments {

    @GET(API.SCHOLARSHIP_COMMENTS)
    fun scholarshipComments(@Query("scholarship_idx") scholarshipIdx: Int?) : Call<JsonElement>

    @POST(API.NEW_COMMENTS)
    fun newComments(@Header("X-ACCESS-TOKEN") xAccessToken: String, @Body params: NewCommentsDTO) : Call<NewCommentsDTO>

    @PATCH(API.DELETE_COMMENTS)
    fun deleteComments(@Header("X-ACCESS-TOKEN") xAccessToken: String, @Path("scholarship_comment_idx") scholarship_comment_idx: Long?, @Body params: DeleteCommentsDTO) : Call<DeleteCommentsDTO>

    @PATCH(API.EDIT_COMMENTS)
    fun editComments(@Header("X-ACCESS-TOKEN") xAccessToken: String, @Path("scholarship_comment_idx") scholarship_comment_idx: Long?, @Body params: EditCommentsDTO) : Call<EditCommentsDTO>
}

// 장학금 myfilter
interface IScholarshipFilter {

    @POST(API.SCHOLARSHIP_FILTER)
    fun scholarshipFilter(@Body params: ScholarshipFilterDTO) : Call<JsonElement>
}

// 장학금 즐겨찾기
interface  IScholarshipBookmark {

    // 장학금 즐겨찾기 유무 조회
    @GET(API.SCHOLARSHIP_BOOKMARK)
    fun scholarshipBookmark(@Header("X-ACCESS-TOKEN") xAccessToken: String, @Path("scholarshipIdx") scholarshipIdx: Int?) : Call<JsonElement>

    // 장학금 추가/삭제
    @POST(API.BOOKMARK_EDIT)
    fun bookmarkEdit(@Header("X-ACCESS-TOKEN") xAccessToken: String, @Path("scholarshipIdx") scholarshipIdx: Int?) : Call<JsonElement>
}
