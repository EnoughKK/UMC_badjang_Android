package com.umc.badjang.PostWritePage

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostWriteApiService {
    @POST("board/add/{user_idx}")
    fun addPost(
        @Header("X-ACCESS-TOKEN") xAccessToken: String,
        @Path("user_idx") userIdx: Int,
        @Body params: PostWriteApiData
    ): Call<PostWriteApiResponse>
}