package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UniversityFilterApiService {
    @POST("/users/info")
    fun addUniversityInfo(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
        @Body params: UniversityFilterAddData
    ): Call<UniversityFilterResponse>
}