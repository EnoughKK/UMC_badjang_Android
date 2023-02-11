package com.umc.badjang.LoginPage.Login

import com.umc.badjang.LoginPage.Login.models.LoginResponse

interface LoginView {
    fun onPostLoginSuccess(response: LoginResponse)
    fun onPostLoginFailure(message: String)
}