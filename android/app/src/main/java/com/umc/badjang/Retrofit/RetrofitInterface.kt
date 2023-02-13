package com.umc.badjang.Retrofit

import com.google.gson.JsonElement
import com.umc.badjang.utils.API
import retrofit2.Call
import retrofit2.http.*

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
}

