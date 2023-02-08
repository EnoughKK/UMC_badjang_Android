package com.umc.badjang.Retrofit

import com.google.gson.JsonElement
import com.umc.badjang.Model.ScholarshipViewCountDTO
import com.umc.badjang.utils.API
import retrofit2.Call
import retrofit2.http.*

// 장학금 조회 (필터사용) 인터페이스
interface ISearchScholarship {

    @GET(API.SEARCH_SCHOLARSHIP)
    fun searchScolarhip(@Query("category") category: String?, @Query("filter") filter: String?, @Query("order") order: String?) : Call<JsonElement>
}

// 지원금 조회 (필터사용) 인터페이스
interface ISearchSupport {

    @GET(API.SEARCH_SUPPORT)
    fun searchSupport(@Query("category") category: String?, @Query("filter") filter: String?, @Query("order") order: String?) : Call<JsonElement>
}

// 장학금인덱스로 장학금조회(조회수1증가) 인터페이스
interface ISholarshipViewCount{

    @GET(API.VIEWCOUNT_SCHOLARSHIP)
    fun searchScholarshipIDx(@Path("scholarshipIdx") scholarshipIdx: Long?) : Call<JsonElement>
}

// 지원금인덱스로 지원금조회(조회수1증가) 인터페이스
interface ISupportViewCount{

    @GET(API.VIEWCOUNT_SUPPORT)
    fun searchSupportIDx(@Path("supportIdx") supportIdx: Long?) : Call<JsonElement>
}

