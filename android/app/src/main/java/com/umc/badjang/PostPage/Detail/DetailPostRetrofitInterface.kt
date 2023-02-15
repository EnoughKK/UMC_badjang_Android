package com.umc.badjang.PostPage.Detail

import com.umc.badjang.PostPage.Board.Model.*
import com.umc.badjang.PostPage.Detail.Model.*
import retrofit2.Call
import retrofit2.http.*

interface DetailPostRetrofitInterface {
    @GET("/board/comment/{post_idx}/{user_idx}")
    fun getComments(@Path("post_idx") post_idx:Int, @Path("user_idx") user_idx:Int): Call<GetCommentsResponse>

    @GET("/board/detail/{user_idx}/{post_idx}")
    fun getOnePost(@Path("user_idx") user_idx:Int, @Path("post_idx") post_idx:Int): Call<GetOnePostResponse>

    @DELETE("/board/delete/{post_idx}/{user_idx}")
    fun postDeletePost(@Path("post_idx") post_idx:Int,
                       @Path("user_idx") user_idx:Int): Call<PostDeletePostResponse>

    @POST("/board/comment/add/{post_idx}/{user_idx}")
    fun postComment(@Path("post_idx") post_idx:Int,
                    @Path("user_idx") user_idx:Int,
                    @Body params: PostCommentReq): Call<PostCommentResponse>

    @GET("/board/school/{schoolNameIdx}/{postIdx}")
    fun getSchoolOnePost(@Path("schoolNameIdx") schoolNameIdx:Int, @Path("postIdx") postIdx:Int): Call<GetSchoolOnePostResponse>

    @DELETE("/board/comment/delete/{comment_idx}/{user_idx}/{post_idx}")
    fun postDeleteComment(@Path("comment_idx") comment_idx:Int,
                       @Path("user_idx") user_idx:Int,
                       @Path("post_idx") post_idx:Int,): Call<PostDeletePostResponse>

    //학교게시판 게시글 삭제
    @DELETE("/board/school/{schoolNameIdx}/delete/{postIdx}")
    fun deleteSchoolPost(@Path("schoolNameIdx") schoolNameIdx:Int,
                       @Path("postIdx") postIdx:Int): Call<PostDeletePostResponse>

    //학교게시판 댓글 달기
    @POST("/board/school/{schoolNameIdx}/comment/add/{postIdx}")
    fun postSchoolComment(@Path("schoolNameIdx") schoolNameIdx:Int,
                    @Path("postIdx") postIdx:Int,
                    @Body params: PostSchoolCommentReq): Call<PostSchoolCommentResponse>
    //학교게시판 댓글 삭제
    @DELETE("/board/school/{schoolNameIdx}/comment/delete/{postIdx}/{commentIdx}")
    fun deleteSchoolComment(@Path("schoolNameIdx") schoolNameIdx:Int,
                          @Path("postIdx") postIdx:Int,
                          @Path("commentIdx") commentIdx:Int,): Call<PostSchoolCommentResponse>
}