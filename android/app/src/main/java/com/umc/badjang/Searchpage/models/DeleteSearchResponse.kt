package com.umc.badjang.Searchpage.models

import com.google.gson.annotations.SerializedName

data class DeleteSearchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)
