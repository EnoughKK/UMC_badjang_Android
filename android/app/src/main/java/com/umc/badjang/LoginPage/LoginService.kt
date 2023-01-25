package com.umc.badjang.LoginPage

import android.util.Log
import com.umc.badjang.ApplicationClass
import com.umc.badjang.LoginPage.models.LoginRequest
import com.umc.badjang.LoginPage.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginActvityInterface: LoginActvityInterface) {
    fun tryPostLogin(loginRequest: LoginRequest){
        val loginRetrofit = ApplicationClass.sRetrofit.create(LoginRetrofit::class.java)
        loginRetrofit.requestLogin(loginRequest).enqueue( object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    //웹통신 실패
                    loginActvityInterface.onPostLoginFailure(t.message?:"통신 오류")
                    Log.e("LOGIN", t.message.toString())
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {


                }
        })

    }
}