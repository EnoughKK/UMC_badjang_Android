package com.umc.badjang.LoginPage

import com.umc.badjang.LoginPage.models.LoginResponse

interface LoginActvityInterface {

    fun onPostLoginSuccess(response: LoginResponse)
    fun onPostLoginFailure(message: String)
}