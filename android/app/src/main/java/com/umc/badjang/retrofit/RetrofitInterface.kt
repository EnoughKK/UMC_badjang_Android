package com.umc.badjang.retrofit

import com.google.gson.JsonElement
import com.umc.badjang.utils.API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    // 장학금 조회 (필터사용)
    @GET(API.SEARCH_SCHOLARSHIP)
    fun searchScolarhip(@Query("category") category: Int?, @Query("filter") filter: Int?, @Query("order") order: Int?) : Call<JsonElement>

}