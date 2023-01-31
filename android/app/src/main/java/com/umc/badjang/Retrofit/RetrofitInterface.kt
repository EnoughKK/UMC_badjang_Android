package com.umc.badjang.Retrofit

import com.google.gson.JsonElement
import com.umc.badjang.Model.ScholarshipViewCountDTO
import com.umc.badjang.utils.API
import retrofit2.Call
import retrofit2.http.*

// 장학금 조회 (필터사용) 인터페이스
interface ISearchScholarship {

    @GET(API.SEARCH_SCHOLARSHIP)
    fun searchScolarhip(@Query("category") category: Int?, @Query("filter") filter: Int?, @Query("order") order: Int?) : Call<JsonElement>
}

// 지원금 조회 (필터사용) 인터페이스
interface ISearchSupport {

    @GET(API.SEARCH_SUPPORT)
    fun searchSupport(@Query("category") category: Int?, @Query("filter") filter: Int?, @Query("order") order: Int?) : Call<JsonElement>
}

// 장학금인덱스로 장학금조회(조회수1증가) 인터페이스
interface ISholarshipViewCount{

    @GET(API.VIEWCOUNT_SCHOLARSHIP)
    fun GetScholarshipViewCount(@Path("scholarshipIdx") scholarshipIdx: Long?) : Call<JsonElement>

    @PATCH(API.VIEWCOUNT_SCHOLARSHIP)
    fun PatchScholarshipViewCount(@Path("scholarshipIdx") scholarshipIdx: Long?, @Field("view") view: Int?) : Call<JsonElement>
}