package com.umc.badjang.Retrofit

import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.gson.JsonElement
import com.google.protobuf.Api
import com.umc.badjang.ApplicationClass
import com.umc.badjang.ScholarshipPage.Model.*
import com.umc.badjang.mConnectUserId
import com.umc.badjang.ApplicationClass
import com.umc.badjang.Model.GetScholarshipDTO
import com.umc.badjang.Model.GetSupportDTO
import com.umc.badjang.Model.ScholarshipViewCountDTO
import com.umc.badjang.Searchpage.SearchRetrofit
import com.umc.badjang.Searchpage.models.ScholarshipData
import com.umc.badjang.utils.API
import com.umc.badjang.utils.Constants.TAG
import com.umc.badjang.utils.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import retrofit2.http.Query

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iSearchScholarship : ISearchScholarship? = SearchScholarshipRC.getClient(API.BASE_URL)?.create(ISearchScholarship::class.java)
    private val iScholarshipViewCount : ISholarshipViewCount? = ScholarshipViewCountRC.getClient(API.BASE_URL)?.create(ISholarshipViewCount::class.java)
    private val iScholarshipComments : IScholarshipComments? = ScholarshipCommentsRC.getClient(API.BASE_URL)?.create(IScholarshipComments::class.java)
    private val iScholarshipFilter : IScholarshipFilter? = ScholarshipFilterRC.getClient(API.BASE_URL)?.create(IScholarshipFilter::class.java)
    private val iScholarshipBookmark : IScholarshipBookmark? = ScholarshipBookmarkRC.getClient(API.BASE_URL)?.create(IScholarshipBookmark::class.java)

    //검색파트 레트로핏 인터페이스
    private val searchRetrofit = ApplicationClass.sRetrofit.create(SearchRetrofit::class.java)

    // 장학금 조회 (필터사용)
    fun searchScholarship(category: String?, filter: String?, order: String?, completion: (RESPONSE_STATE, ArrayList<GetScholarshipDTO>?) -> Unit){

        val call = iSearchScholarship?.searchScolarhip(category = category, filter = filter, order = order).let {
            it
        }?: return

        call.enqueue(object  : retrofit2.Callback<JsonElement>{

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

                response.body()?.let {

                    var parsedScholarshipDataArray = ArrayList<GetScholarshipDTO>()

                    val body = it.asJsonObject
                    val results = body.getAsJsonArray("result")

                    results.forEach { resultItem ->
                        val resultItemObject = resultItem.asJsonObject

                        @NonNull
                        val scholarship_idx = resultItemObject.get("scholarship_idx").asLong

                        @NonNull
                        val scholarship_name = resultItemObject.get("scholarship_name").asString

                        @Nullable
                        val scholarship_institution: String
                        if (resultItemObject.get("scholarship_institution").isJsonNull){
                            scholarship_institution = "기관정보 없음"
                        } else {
                            scholarship_institution = resultItemObject.get("scholarship_institution").asString
                        }

                        val scholarship_content: String
                        if (resultItemObject.get("scholarship_content").isJsonNull){
                            scholarship_content = "내용 없음"
                        } else {
                            scholarship_content = resultItemObject.get("scholarship_content").asString
                        }

                        val scholarship_image: String
                        if (resultItemObject.get("scholarship_image").isJsonNull){
                            scholarship_image = ""
                        } else {
                            scholarship_image = resultItemObject.get("scholarship_image").asString
                        }

                        val scholarship_homepage: String
                        if (resultItemObject.get("scholarship_homepage").isJsonNull){
                            scholarship_homepage = ""
                        } else {
                            scholarship_homepage = resultItemObject.get("scholarship_homepage").asString
                        }

                        val scholarship_view = resultItemObject.get("scholarship_view").asInt

                        val scholarship_comment = resultItemObject.get("scholarship_comment").asInt

                        val scholarship_scale: String
                        if (resultItemObject.get("scholarship_scale").isJsonNull){
                            scholarship_scale = ""
                        } else {
                            scholarship_scale = resultItemObject.get("scholarship_scale").asString
                        }

                        val scholarship_term: String
                        if (resultItemObject.get("scholarship_term").isJsonNull){
                            scholarship_term = ""
                        } else {
                            scholarship_term = resultItemObject.get("scholarship_term").asString
                        }

                        val scholarship_presentation: String
                        if (resultItemObject.get("scholarship_presentation").isJsonNull){
                            scholarship_presentation = ""
                        } else {
                            scholarship_presentation = resultItemObject.get("scholarship_presentation").asString
                        }

                        val scholarship_univ: String
                        if (resultItemObject.get("scholarship_univ").isJsonNull){
                            scholarship_univ = "대학이름 없음"
                        } else {
                            scholarship_univ = resultItemObject.get("scholarship_univ").asString
                        }

                        val scholarship_category: String
                        if (resultItemObject.get("scholarship_category").isJsonNull){
                            scholarship_category = "카테고리 없음"
                        } else {
                            scholarship_category = resultItemObject.get("scholarship_category").asString
                        }

                        val scholarshipItem = GetScholarshipDTO(
                            scholarship_idx = scholarship_idx,
                            scholarship_name = scholarship_name,
                            scholarship_institution = scholarship_institution,
                            scholarship_content = scholarship_content,
                            scholarship_image = scholarship_image,
                            scholarship_homepage = scholarship_homepage,
                            scholarship_view = scholarship_view,
                            scholarship_comment = scholarship_comment,
                            scholarship_scale = scholarship_scale,
                            scholarship_term = scholarship_term,
                            scholarship_presentation = scholarship_presentation,
                            scholarship_univ = scholarship_univ,
                            scholarship_category = scholarship_category
                        )
                        parsedScholarshipDataArray.add(scholarshipItem)

                    }

                    completion(RESPONSE_STATE.OKAY, parsedScholarshipDataArray)
                }

            }

            // 응답 실패시시
           override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATE.FAIL, null)
            }

        })

    }

    // 장학금 인덱스로 조회
    fun searchScholarshipIDx(scholarshipIdx: Long?, completion: (RESPONSE_STATE, ArrayList<GetScholarshipDTO>?) -> Unit){

        val call = iScholarshipViewCount?.searchScholarshipIDx(scholarshipIdx = scholarshipIdx).let {
            it
        }?: return

        call.enqueue(object  : retrofit2.Callback<JsonElement>{

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response : 장학금 인덱스로 조회 ${response.body()}}")

                response.body()?.let {

                    var parsedScholarshipDataArray = ArrayList<GetScholarshipDTO>()

                    val body = it.asJsonObject
                    val resultItemObject = body.get("result").asJsonObject

                    @NonNull
                    val scholarship_idx = resultItemObject.get("scholarship_idx").asLong

                    @NonNull
                    val scholarship_name = resultItemObject.get("scholarship_name").asString

                    @Nullable
                    val scholarship_institution: String
                    if (resultItemObject.get("scholarship_institution").isJsonNull) {
                        scholarship_institution = "기관정보 없음"
                    } else {
                        scholarship_institution =
                            resultItemObject.get("scholarship_institution").asString
                    }

                    val scholarship_content: String
                    if (resultItemObject.get("scholarship_content").isJsonNull) {
                        scholarship_content = "내용 없음"
                    } else {
                        scholarship_content =
                            resultItemObject.get("scholarship_content").asString
                    }

                    val scholarship_image: String
                    if (resultItemObject.get("scholarship_image").isJsonNull) {
                        scholarship_image = ""
                    } else {
                        scholarship_image = resultItemObject.get("scholarship_image").asString
                    }

                    val scholarship_homepage: String
                    if (resultItemObject.get("scholarship_homepage").isJsonNull) {
                        scholarship_homepage = ""
                    } else {
                        scholarship_homepage =
                            resultItemObject.get("scholarship_homepage").asString
                    }

                    val scholarship_view = resultItemObject.get("scholarship_view").asInt

                    val scholarship_comment = resultItemObject.get("scholarship_comment").asInt

                    val scholarship_scale: String
                    if (resultItemObject.get("scholarship_scale").isJsonNull) {
                        scholarship_scale = ""
                    } else {
                        scholarship_scale = resultItemObject.get("scholarship_scale").asString
                    }

                    val scholarship_term: String
                    if (resultItemObject.get("scholarship_term").isJsonNull) {
                        scholarship_term = ""
                    } else {
                        scholarship_term = resultItemObject.get("scholarship_term").asString
                    }

                    val scholarship_presentation: String
                    if (resultItemObject.get("scholarship_presentation").isJsonNull) {
                        scholarship_presentation = ""
                    } else {
                        scholarship_presentation =
                            resultItemObject.get("scholarship_presentation").asString
                    }

                    val scholarship_univ: String
                    if (resultItemObject.get("scholarship_univ").isJsonNull) {
                        scholarship_univ = "대학이름 없음"
                    } else {
                        scholarship_univ = resultItemObject.get("scholarship_univ").asString
                    }

                    val scholarship_category: String
                    if (resultItemObject.get("scholarship_category").isJsonNull) {
                        scholarship_category = "카테고리 없음"
                    } else {
                        scholarship_category =
                            resultItemObject.get("scholarship_category").asString
                    }

                    val scholarshipItem = GetScholarshipDTO(
                        scholarship_idx = scholarship_idx,
                        scholarship_name = scholarship_name,
                        scholarship_institution = scholarship_institution,
                        scholarship_content = scholarship_content,
                        scholarship_image = scholarship_image,
                        scholarship_homepage = scholarship_homepage,
                        scholarship_view = scholarship_view,
                        scholarship_comment = scholarship_comment,
                        scholarship_scale = scholarship_scale,
                        scholarship_term = scholarship_term,
                        scholarship_presentation = scholarship_presentation,
                        scholarship_univ = scholarship_univ,
                        scholarship_category = scholarship_category
                    )
                    parsedScholarshipDataArray.add(scholarshipItem)

                    completion(RESPONSE_STATE.OKAY, parsedScholarshipDataArray)

                }
            }
            // 응답 실패시시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATE.FAIL, null)
            }

        })

    }

    // 장학금 댓글 조회
    fun scholarshipComments(scholarshipIdx: Int?, completion: (RESPONSE_STATE, ArrayList<ScholarshipCommentsDTO>?) -> Unit) {

        val call = iScholarshipComments?.scholarshipComments(scholarshipIdx = scholarshipIdx).let {
            it
        }?: return

        call.enqueue(object  : retrofit2.Callback<JsonElement>{

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response : 장학금 댓글 조회 ${response.body()}}")

                response.body()?.let {

                    var parsedScholarshipDataArray = ArrayList<ScholarshipCommentsDTO>()

                    val body = it.asJsonObject
                    val results = body.getAsJsonArray("result")
                    var viewType: Int

                    results.forEach { resultItem ->
                        val resultItemObject = resultItem.asJsonObject

                        @NonNull
                        val scholarship_comment_idx = resultItemObject.get("scholarship_comment_idx").asInt

                        @NonNull
                        val scholarship_idx = resultItemObject.get("scholarship_idx").asInt

                        @NonNull
                        val user_idx = resultItemObject.get("user_idx").asInt

                        val user_name = resultItemObject.get("user_name").asString

                        val user_profileimage_url = resultItemObject.get("user_profileimage_url").asString

                        @NonNull
                        val scholarship_comment_content = resultItemObject.get("scholarship_comment_content").asString

                        @NonNull
                        val scholarship_comment_updateAt = resultItemObject.get("scholarship_comment_updateAt").asString

                        if(user_idx == mConnectUserId) {
                            viewType = 1
                        } else {
                            viewType = 2
                        }


                        val scholarshipItem = ScholarshipCommentsDTO(
                            scholarship_idx = scholarship_idx,
                            scholarship_comment_idx = scholarship_comment_idx,
                            user_idx = user_idx,
                            user_name = user_name,
                            user_profileimage_url = user_profileimage_url,
                            scholarship_comment_content = scholarship_comment_content,
                            scholarship_comment_updateAt = scholarship_comment_updateAt,
                            viewType = viewType
                        )
                        parsedScholarshipDataArray.add(scholarshipItem)

                    }
                    completion(RESPONSE_STATE.OKAY, parsedScholarshipDataArray)

                }
            }
            // 응답 실패시시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATE.FAIL, null)
            }

        })
    }

    // 장학금 댓글 생성
    fun newComments(xAccessToken : String?, params : NewCommentsDTO) {

        val call = iScholarshipComments?.newComments(xAccessToken = xAccessToken!!, params = params).let {
            it
        }?: return

        call.enqueue(object  : Callback<NewCommentsDTO> {

            override fun onResponse(call: Call<NewCommentsDTO>, response: Response<NewCommentsDTO>) {
                Log.d(TAG, "onResponse: 새로운 댓글 생성")
            }

            override fun onFailure(call: Call<NewCommentsDTO>, t: Throwable) {
                Log.d(TAG, "onFailure: 새로운 댓글 생성 실패 t: $t")
            }

        })

    }

    // 장학금 댓글 삭제
    fun deleteComments(xAccessToken : String?, scholarship_comment_idx: Long?, params: DeleteCommentsDTO) {

        val call = iScholarshipComments?.deleteComments(xAccessToken = xAccessToken!!, scholarship_comment_idx = scholarship_comment_idx, params = params).let {
            it
        }?: return

        call.enqueue(object  : Callback<DeleteCommentsDTO>{
            override fun onResponse(call: Call<DeleteCommentsDTO>, response: Response<DeleteCommentsDTO>) {

                Log.d(TAG, "RetrofitManager - onResponse() called / response : 댓글 삭제 ${params.toString()}}")
                Log.d(TAG, "onResponse: 댓글 삭제")
            }

            override fun onFailure(call: Call<DeleteCommentsDTO>, t: Throwable) {

            }

        })
    }

    // 장학금 댓글 수정
    fun editComments(xAccessToken : String?, scholarship_comment_idx: Long?, params: EditCommentsDTO) {

        val call = iScholarshipComments?.editComments(xAccessToken = xAccessToken!!, scholarship_comment_idx = scholarship_comment_idx, params = params).let {
            it
        }?: return

        call.enqueue(object  : Callback<EditCommentsDTO>{
            override fun onResponse(call: Call<EditCommentsDTO>, response: Response<EditCommentsDTO>) {

                Log.d(TAG, "RetrofitManager - onResponse() called / response : 댓글 수정 ${params.toString()}}")
                Log.d(TAG, "onResponse: 댓글 수정")
            }

            override fun onFailure(call: Call<EditCommentsDTO>, t: Throwable) {

            }

        })

    }

    // 장학금 필터
    fun scholarshipFilter(params: ScholarshipFilterDTO, completion: (RESPONSE_STATE, ArrayList<GetScholarshipDTO>?) -> Unit) {

        val call = iScholarshipFilter?.scholarshipFilter(params = params).let {
            it
        }?: return

        call.enqueue(object  : retrofit2.Callback<JsonElement>{

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

                response.body()?.let {

                    var parsedScholarshipDataArray = ArrayList<GetScholarshipDTO>()

                    val body = it.asJsonObject
                    val results = body.getAsJsonArray("result")

                    results.forEach { resultItem ->
                        val resultItemObject = resultItem.asJsonObject

                        @NonNull
                        val scholarship_idx = resultItemObject.get("scholarship_idx").asLong

                        @NonNull
                        val scholarship_name = resultItemObject.get("scholarship_name").asString

                        @Nullable
                        val scholarship_institution: String
                        if (resultItemObject.get("scholarship_institution").isJsonNull){
                            scholarship_institution = "기관정보 없음"
                        } else {
                            scholarship_institution = resultItemObject.get("scholarship_institution").asString
                        }

                        val scholarship_content: String
                        if (resultItemObject.get("scholarship_content").isJsonNull){
                            scholarship_content = "내용 없음"
                        } else {
                            scholarship_content = resultItemObject.get("scholarship_content").asString
                        }

                        val scholarship_image: String
                        if (resultItemObject.get("scholarship_image").isJsonNull){
                            scholarship_image = ""
                        } else {
                            scholarship_image = resultItemObject.get("scholarship_image").asString
                        }

                        val scholarship_homepage: String
                        if (resultItemObject.get("scholarship_homepage").isJsonNull){
                            scholarship_homepage = ""
                        } else {
                            scholarship_homepage = resultItemObject.get("scholarship_homepage").asString
                        }

                        val scholarship_view = resultItemObject.get("scholarship_view").asInt

                        val scholarship_comment = resultItemObject.get("scholarship_comment").asInt

                        val scholarship_scale: String
                        if (resultItemObject.get("scholarship_scale").isJsonNull){
                            scholarship_scale = ""
                        } else {
                            scholarship_scale = resultItemObject.get("scholarship_scale").asString
                        }

                        val scholarship_term: String
                        if (resultItemObject.get("scholarship_term").isJsonNull){
                            scholarship_term = ""
                        } else {
                            scholarship_term = resultItemObject.get("scholarship_term").asString
                        }

                        val scholarship_presentation: String
                        if (resultItemObject.get("scholarship_presentation").isJsonNull){
                            scholarship_presentation = ""
                        } else {
                            scholarship_presentation = resultItemObject.get("scholarship_presentation").asString
                        }

                        val scholarship_univ: String
                        if (resultItemObject.get("scholarship_univ").isJsonNull){
                            scholarship_univ = "대학이름 없음"
                        } else {
                            scholarship_univ = resultItemObject.get("scholarship_univ").asString
                        }

                        val scholarship_category: String
                        if (resultItemObject.get("scholarship_category").isJsonNull){
                            scholarship_category = "카테고리 없음"
                        } else {
                            scholarship_category = resultItemObject.get("scholarship_category").asString
                        }

                        val scholarshipItem = GetScholarshipDTO(
                            scholarship_idx = scholarship_idx,
                            scholarship_name = scholarship_name,
                            scholarship_institution = scholarship_institution,
                            scholarship_content = scholarship_content,
                            scholarship_image = scholarship_image,
                            scholarship_homepage = scholarship_homepage,
                            scholarship_view = scholarship_view,
                            scholarship_comment = scholarship_comment,
                            scholarship_scale = scholarship_scale,
                            scholarship_term = scholarship_term,
                            scholarship_presentation = scholarship_presentation,
                            scholarship_univ = scholarship_univ,
                            scholarship_category = scholarship_category
                        )
                        parsedScholarshipDataArray.add(scholarshipItem)

                    }

                    completion(RESPONSE_STATE.OKAY, parsedScholarshipDataArray)
                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }

        })

    }

    // 장학금 즐겨찾기
    fun scholarshipBookmark(xAccessToken : String?, scholarshipIdx: Int, completion: (RESPONSE_STATE, ArrayList<ScholarshipBookmarkDTO>?) -> Unit) {

        val call = iScholarshipBookmark?.scholarshipBookmark(xAccessToken = xAccessToken!!, scholarshipIdx = scholarshipIdx).let {
            it
        }?: return

        call.enqueue(object : Callback<JsonElement>{

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                response.body()?.let {

                    var parsedScholarshipDataArray = ArrayList<ScholarshipBookmarkDTO>()

                    val body = it.asJsonObject
                    val resultItemObject = body.get("result").asJsonObject

                    val scholarship_idx = resultItemObject.get("scholarship_idx").asInt

                    val bookmark_check = resultItemObject.get("bookmark_check").asString

                    val scholarshipBookmarkItem = ScholarshipBookmarkDTO (
                        scholarship_idx = scholarship_idx,
                        bookmark_check = bookmark_check
                    )

                    parsedScholarshipDataArray.add(scholarshipBookmarkItem)

                    completion(RESPONSE_STATE.OKAY, parsedScholarshipDataArray)

                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

        })

    }

    fun bookmarkEdit(xAccessToken : String?, scholarshipIdx: Int) {

        val call = iScholarshipBookmark?.bookmarkEdit(xAccessToken = xAccessToken!!, scholarshipIdx = scholarshipIdx).let {
            it
        }?: return

        call.enqueue(object  : Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager - onResponse() called / response : 장학금 즐겨찾기 추가 ${response}}")
                Log.d(TAG, "onResponse: 장학금 즐겨찾기 추가")
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }

        })
    }

    fun scholarshipsearch(query: String?, completion: (RESPONSE_STATE, ArrayList<ScholarshipData>?) -> Unit){

        val term = query.let{
            it
        }?: ""

        val call = searchRetrofit?.scholarshipsearch(query = term).let{
            it
        }?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            //응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"RetrofitManager - onFailure() called / t $t")

                completion(RESPONSE_STATE.FAIL,null)
            }

            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"RetrofitManager - onResponse() called / response : ${response.body()}")

                when(response.code()){
                    1000 -> {
                        response.body()?.let {
                            var ScholarshipDataArray = ArrayList<ScholarshipData>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("result")
                            val code = body.get("code").asInt
                            if(code == 3000){
                                completion(RESPONSE_STATE.NO_CONTENT, null)
                            }
                            else {
                                results.forEach { resultItem ->
                                    val resultItemObject = resultItem.asJsonObject

                                    @NonNull
                                    val scholarship_idx = resultItemObject.get("scholarship_idx").asLong

                                    @NonNull
                                    val scholarship_name = resultItemObject.get("scholarship_name").asString

                                    @Nullable
                                    val scholarship_institution: String
                                    if (resultItemObject.get("scholarship_institution").isJsonNull){
                                        scholarship_institution = "기관정보 없음"
                                    } else {
                                        scholarship_institution = resultItemObject.get("scholarship_institution").asString
                                    }

                                    val scholarship_content: String
                                    if (resultItemObject.get("scholarship_content").isJsonNull){
                                        scholarship_content = "내용 없음"
                                    } else {
                                        scholarship_content = resultItemObject.get("scholarship_content").asString
                                    }

                                    val scholarship_image: String
                                    if (resultItemObject.get("scholarship_image").isJsonNull){
                                        scholarship_image = ""
                                    } else {
                                        scholarship_image = resultItemObject.get("scholarship_image").asString
                                    }

                                    val scholarship_homepage: String
                                    if (resultItemObject.get("scholarship_homepage").isJsonNull){
                                        scholarship_homepage = ""
                                    } else {
                                        scholarship_homepage = resultItemObject.get("scholarship_homepage").asString
                                    }

                                    val scholarship_view = resultItemObject.get("scholarship_view").asInt

                                    val scholarship_comment = resultItemObject.get("scholarship_comment").asInt

                                    val scholarship_scale: String
                                    if (resultItemObject.get("scholarship_scale").isJsonNull){
                                        scholarship_scale = ""
                                    } else {
                                        scholarship_scale = resultItemObject.get("scholarship_scale").asString
                                    }

                                    val scholarship_term: String
                                    if (resultItemObject.get("scholarship_term").isJsonNull){
                                        scholarship_term = ""
                                    } else {
                                        scholarship_term = resultItemObject.get("scholarship_term").asString
                                    }

                                    val scholarship_presentation: String
                                    if (resultItemObject.get("scholarship_presentation").isJsonNull){
                                        scholarship_presentation = ""
                                    } else {
                                        scholarship_presentation = resultItemObject.get("scholarship_presentation").asString
                                    }

                                    val scholarship_univ: String
                                    if (resultItemObject.get("scholarship_univ").isJsonNull){
                                        scholarship_univ = "대학이름 없음"
                                    } else {
                                        scholarship_univ = resultItemObject.get("scholarship_univ").asString
                                    }

                                    val scholarship_category: String
                                    if (resultItemObject.get("scholarship_category").isJsonNull){
                                        scholarship_category = "카테고리 없음"
                                    } else {
                                        scholarship_category = resultItemObject.get("scholarship_category").asString
                                    }

                                    val scholarshipItem = ScholarshipData(
                                        scholarship_idx = scholarship_idx,
                                        scholarship_name = scholarship_name,
                                        scholarship_institution = scholarship_institution,
                                        scholarship_content = scholarship_content,
                                        scholarship_image = scholarship_image,
                                        scholarship_homepage = scholarship_homepage,
                                        scholarship_view = scholarship_view,
                                        scholarship_comment = scholarship_comment,
                                        scholarship_scale = scholarship_scale,
                                        scholarship_term = scholarship_term,
                                        scholarship_presentation = scholarship_presentation,
                                    )
                                    ScholarshipDataArray.add(scholarshipItem)
                                }
                                completion(RESPONSE_STATE.OKAY,ScholarshipDataArray)

                            }
                        }
                    }
                }
            }



        })
    }

}