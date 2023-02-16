package com.umc.badjang.PostPage.Board

import com.umc.badjang.PostPage.Board.Model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostBoardRetrofitInterface {
    @GET("/board/{user_idx}")
    fun getPostBoard(@Path("user_idx") user_idx:Int): Call<GetPostBoardResponse>

    @GET("/board/school")
    fun getSchoolPostBoard(): Call<GetSchoolPostBoardResponse>

    @GET("/popularBoard")
    fun getPopular2PostBoard(): Call<GetPopularPostBoardResponse>

    @GET("/popularBoardAll")
    fun getPopularPostBoard(): Call<GetAllPopularPostBoardResponse>

    @GET("/board/school/{schoolNameIdx}")
    fun getSchoolAllPost(@Path("schoolNameIdx") schoolNameIdx:Int): Call<GetAllSchoolPostResponse>
}