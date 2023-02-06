package com.umc.badjang.LoginPage.Kakao

import android.util.Log
import com.umc.badjang.ApplicationClass
import com.umc.badjang.LoginPage.Kakao.models.KakaoSignupRequest
import com.umc.badjang.LoginPage.Kakao.models.KakaoSignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KakaoSignupService(val view: KakaoSignupView) {
    fun tryKakaoPostSignup(postSignupRequest: KakaoSignupRequest){
        val kakaoRetrofitInterface = ApplicationClass.sRetrofit.create(KakaoRetrofitInterface::class.java)
        kakaoRetrofitInterface.postKakaoSignup(postSignupRequest).enqueue(object:
            Callback<KakaoSignupResponse> {
            override fun onResponse(
                call: Call<KakaoSignupResponse>, response: Response<KakaoSignupResponse>
            ) {
                if(response.body() != null) {
                    view.onKakaoPostSignUpSuccess(response.body() as KakaoSignupResponse)
                    Log.d("Success", "-----카카오 access token 통신성공-----")

                    // 로그인 된 사용자의 idx를 SharedPreferences로 로컬 DB에 저장
                    //Log.d("user_idx", response.body()!!.result.user_idx.toString())
                    ApplicationClass.prefs.setString("user_idx", response.body()!!.result.user_idx.toString())
                }
            }

            override fun onFailure(call: Call<KakaoSignupResponse>, t: Throwable) {
                view.onKakaoPostSignUpFailure(t.message ?: "통신 오류")
                Log.d("Fail","-----통신실패-----")
            }


        }
        )
    }
}