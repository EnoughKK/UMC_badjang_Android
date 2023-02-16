package com.umc.badjang.MyPage.FQA

import com.umc.badjang.MyPage.FQA.Model.FQADetailRes
import com.umc.badjang.MyPage.FQA.Model.FQARes
import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.Model.PostProfileModifyReq
import com.umc.badjang.MyPage.Model.PostUserInfoModify
import com.umc.badjang.MyPage.MyWrite.model.MyWriteChatRes
import com.umc.badjang.MyPage.MyWrite.model.MyWriteRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FQARetrofitInterface {
    @GET("/FAQs")
    fun getFQA(): Call<FQARes>

    @GET("/FAQs/{FAQIdx}")
    fun getDetailFQA(@Path("FAQIdx") FAQIdx:Long): Call<FQADetailRes>
}