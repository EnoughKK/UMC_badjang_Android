package com.umc.badjang.Settings.Myinfo.models

import com.google.gson.annotations.SerializedName

data class MyinfoRequest(
    @SerializedName("user_idx") val user_idx: Int,
    @SerializedName("user_name") val user_name: String?,
    @SerializedName("user_phone") val user_phone: String?
)
