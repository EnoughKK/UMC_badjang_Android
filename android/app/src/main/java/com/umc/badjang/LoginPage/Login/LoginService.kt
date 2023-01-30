package com.umc.badjang.LoginPage.Login

import android.util.Log
import com.umc.badjang.ApplicationClass
import com.umc.badjang.LoginPage.Login.models.LoginRequest
import com.umc.badjang.LoginPage.Login.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService (val view: LoginView) {
    fun tryPostSignIn(postSignInRequest: LoginRequest){
        val loginRetrofit = ApplicationClass.sRetrofit.create(LoginRetrofit::class.java)
        loginRetrofit.requestLogin(postSignInRequest).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.body() != null){
                    Log.d("Success","-----통신성공-----")
                    view.onPostLoginSuccess(response.body() as LoginResponse)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.onPostLoginFailure(t.message ?: "통신 오류")
                Log.d("Fail","-----통신실패-----")
            }
        })
    }
}