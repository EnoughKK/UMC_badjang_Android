package com.umc.badjang.Retrofit

import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.gson.JsonElement
import com.umc.badjang.Model.GetScholarshipDTO
import com.umc.badjang.Model.GetSupportDTO
import com.umc.badjang.Model.ScholarshipViewCountDTO
import com.umc.badjang.utils.API
import com.umc.badjang.utils.Constants.TAG
import com.umc.badjang.utils.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iSearchScholarship : ISearchScholarship? = SearchScholarshipRC.getClient(API.BASE_URL)?.create(ISearchScholarship::class.java)
    private val iSearchSupport : ISearchSupport? = SearchSupportRC.getClient(API.BASE_URL)?.create(ISearchSupport::class.java)
    private val iScholarshipViewCount : ISholarshipViewCount? = ScholarshipViewCountRC.getClient(API.BASE_URL)?.create(ISholarshipViewCount::class.java)

    // 장학금 조회 (필터사용)
    fun searchScholarship(category: Int?, filter: Int?, order: Int?, completion: (RESPONSE_STATE, ArrayList<GetScholarshipDTO>?) -> Unit){

        val call = iSearchScholarship?.searchScolarhip(category = category, filter = filter, order = order).let {
            it
        }?: return

        call.enqueue(object  : retrofit2.Callback<JsonElement>{

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

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

    // 장학금 조회 (필터사용)
    fun searchSupport(category: Int?, filter: Int?, order: Int?, completion: (RESPONSE_STATE, ArrayList<GetSupportDTO>?) -> Unit){

        val call = iSearchSupport?.searchSupport(category = category, filter = filter, order = order).let {
            it
        }?: return

        call.enqueue(object  : retrofit2.Callback<JsonElement>{

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

                response.body()?.let {

                    var parsedSupportDataArray = ArrayList<GetSupportDTO>()

                    val body = it.asJsonObject
                    val results = body.getAsJsonArray("result")

                    results.forEach { resultItem ->
                        val resultItemObject = resultItem.asJsonObject

                        @NonNull
                        val support_idx = resultItemObject.get("support_idx").asLong

                        @NonNull
                        val support_name = resultItemObject.get("support_name").asString

                        @Nullable
                        val support_institution: String
                        if (resultItemObject.get("support_institution").isJsonNull){
                            support_institution = "기관정보 없음"
                        } else {
                            support_institution = resultItemObject.get("support_institution").asString
                        }

                        val support_content: String
                        if (resultItemObject.get("support_content").isJsonNull){
                            support_content = "내용 없음"
                        } else {
                            support_content = resultItemObject.get("support_content").asString
                        }

                        val support_image: String
                        if (resultItemObject.get("support_image").isJsonNull){
                            support_image = ""
                        } else {
                            support_image = resultItemObject.get("support_image").asString
                        }

                        val support_homepage: String
                        if (resultItemObject.get("support_homepage").isJsonNull){
                            support_homepage = ""
                        } else {
                            support_homepage = resultItemObject.get("support_homepage").asString
                        }

                        val support_view = resultItemObject.get("support_view").asInt

                        val support_comment = resultItemObject.get("support_comment").asInt

                        val support_scale: String
                        if (resultItemObject.get("support_scale").isJsonNull){
                            support_scale = ""
                        } else {
                            support_scale = resultItemObject.get("support_scale").asString
                        }

                        val support_term: String
                        if (resultItemObject.get("support_term").isJsonNull){
                            support_term = ""
                        } else {
                            support_term = resultItemObject.get("support_term").asString
                        }

                        val support_presentation: String
                        if (resultItemObject.get("support_presentation").isJsonNull){
                            support_presentation = ""
                        } else {
                            support_presentation = resultItemObject.get("support_presentation").asString
                        }

                        val support_univ: String
                        if (resultItemObject.get("support_univ").isJsonNull){
                            support_univ = "대학이름 없음"
                        } else {
                            support_univ = resultItemObject.get("support_univ").asString
                        }

                        val support_category: String
                        if (resultItemObject.get("support_category").isJsonNull){
                            support_category = "카테고리 없음"
                        } else {
                            support_category = resultItemObject.get("support_category").asString
                        }

                        val supportItem = GetSupportDTO(
                            support_idx = support_idx,
                            support_name = support_name,
                            support_institution = support_institution,
                            support_content = support_content,
                            support_image = support_image,
                            support_homepage = support_homepage,
                            support_view = support_view,
                            support_comment = support_comment,
                            support_scale = support_scale,
                            support_term = support_term,
                            support_presentation = support_presentation,
                            support_univ = support_univ,
                            support_category = support_category
                        )
                        parsedSupportDataArray.add(supportItem)

                    }

                    completion(RESPONSE_STATE.OKAY, parsedSupportDataArray)
                }

            }

            // 응답 실패시시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATE.FAIL, null)
            }

        })

    }

    // 장학금 인덱스로 조회 (조회수 증가)
    fun scholarshipViewCount(scholarshipIdx: Long?, completion: (RESPONSE_STATE, ArrayList<ScholarshipViewCountDTO>?) -> Unit){

        val call = iScholarshipViewCount?.PatchScholarshipViewCount(scholarshipIdx = scholarshipIdx).let {
            it
        }?: return

        call.enqueue(object  : retrofit2.Callback<JsonElement>{

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

                response.body()?.let {

                    var parsedSupportDataArray = ArrayList<ScholarshipViewCountDTO>()

                    val body = it.asJsonObject
                    val results = body.getAsJsonArray("result")

                    results.forEach { resultItem ->
                        val resultItemObject = resultItem.asJsonObject

                        @NonNull
                        val scholarship_idx = resultItemObject.get("scholarship_idx").asLong

                        @NonNull
                        val scholarship_view = resultItemObject.get("scholarship_view").asInt

                        val supportItem = ScholarshipViewCountDTO(
                            scholarship_idx = scholarship_idx,
                            scholarship_view = scholarship_view
                        )
                        parsedSupportDataArray.add(supportItem)
                    }

                    completion(RESPONSE_STATE.OKAY, parsedSupportDataArray)
                }

            }

            // 응답 실패시시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATE.FAIL, null)
            }

        })

    }

}