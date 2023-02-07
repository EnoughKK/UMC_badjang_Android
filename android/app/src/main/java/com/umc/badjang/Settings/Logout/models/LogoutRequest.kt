package com.umc.badjang.Settings.Logout.models

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("user_idx") val user_idx: Int
    )
