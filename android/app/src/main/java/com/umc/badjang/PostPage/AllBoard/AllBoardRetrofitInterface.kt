package com.umc.badjang.PostPage.AllBoard

import com.umc.badjang.PostPage.AllBoard.Model.GetAllPostBoardResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AllBoardRetrofitInterface {
    @GET("/board/school")
    fun getAllSchoolPostBoard(): Call<GetAllPostBoardResponse>

}