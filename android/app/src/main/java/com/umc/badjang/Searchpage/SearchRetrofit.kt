package com.umc.badjang.Searchpage

import com.google.gson.JsonElement
import com.umc.badjang.Searchpage.models.*
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofit {

    //전체검색 api
    @GET("/search/all?query=")
    fun allsearch( @Query("query") query:String) : Call<AllSearchResponse>

    //게시판 검색 api
    @GET("/search/board?query=")
    fun boardsearch(@Query("query") query: String) : Call<BoardSearchResponse>

    //장학금 검색 api
    @GET("/search/scholarship?query=")
    fun scholarshipsearch(@Query("query") query: String) : Call<JsonElement>

    //지원금 검색 api
    @GET("/search/support?query=")
    fun supportsearch(@Query("query") query: String) : Call<SupportSearchResponse>

    //최근 검색어 api
    @GET("/search")
    fun recentsearch(@Header("X-ACCESS-TOKEN") jwt:String) : Call<RecentSearchResponse>

    //최근 검색어 삭제 api
    @DELETE("/search/delete/:searchHistoryIdx")
    fun deletesearch(
        @Header("X-ACCESS-TOKEN") jwt:String,
        @Path("searchHistoryIdx") searchHistoryIdx:Long) : Call<DeleteSearchResponse>
}