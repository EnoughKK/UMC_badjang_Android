package com.umc.badjang.HomePagaApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// 게시글 조회
interface PostIdxApiService {
    @GET("board/detail/{user_idx}/{post_idx}")
    fun getPostData(
        @Path("user_idx") userIdx: Int,
        @Path("post_idx") postIdx: Int
    ): Call<PostIdxApiData>
}