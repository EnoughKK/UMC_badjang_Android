package com.umc.badjang.Settings

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : Result
)

data class Result(
    @SerializedName("user_idx") val user_idx : Int,
    @SerializedName("user_email") val user_email : String,
    @SerializedName("user_name") val user_name:String,
    @SerializedName("user_profileimage_url") val user_profileimage_url :String,
    @SerializedName("user_type") val user_type:String,
    @SerializedName("user_grade") val user_grade :String,
    @SerializedName("user_phone") val user_phone:String,
    @SerializedName("user_birth") val user_birth:String,
    @SerializedName("user_push_yn") val user_on_off:String,
    @SerializedName("user_college") val user_college:String,
    @SerializedName("user_univ") val user_univ:String,
    @SerializedName("user_department") val user_department:String,
    @SerializedName("user_semester") val user_semester:String,
    @SerializedName("user_city") val user_city:String,
    @SerializedName("user_province") val user_province:String

)
