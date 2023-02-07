package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

// 장학금 idx로 장학금 정보 조회하는 Api
interface ScholarshipIdxApiService {
    @GET("scholarships/{scholarshipIdx}")
    fun getScholarshipData(
        @Path("scholarshipIdx") scholarshipIdx: Long
    ): Call<ScholarshipIdxApiData>
}