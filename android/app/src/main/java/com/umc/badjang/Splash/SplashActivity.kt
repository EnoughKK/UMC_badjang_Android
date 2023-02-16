package com.umc.badjang.Splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

//develop 브랜치 추가
import android.util.Log
import com.umc.badjang.ApplicationClass
import com.umc.badjang.ApplicationClass.Companion.USER_IDX
import com.umc.badjang.ApplicationClass.Companion.bSharedPreferences
import com.umc.badjang.ApplicationClass.Companion.sSharedPreferences
import com.umc.badjang.LoginPage.LoginActivity
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.MyProfileRetrofitInterface
import com.umc.badjang.Splash.Model.PostAutoLoginReq
import com.umc.badjang.Splash.Model.PostAutoLoginResponse
import com.umc.badjang.databinding.ActivityMainBinding
import com.umc.badjang.databinding.ActivitySplashBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding // viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩 초기화
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            getMyProfile(PostAutoLoginReq(user_idx = bSharedPreferences.getInt(USER_IDX,0)))
        }, 1000)
    }

    private fun getMyProfile(postAutoLoginReq: PostAutoLoginReq){
        //Log.d("postScholarship", "호출은 된다.")
        val splashInterface = ApplicationClass.sRetrofit.create(SplashRetrofitInterface::class.java)
        splashInterface.postAutoLogin(postAutoLoginReq).enqueue(object :
            Callback<PostAutoLoginResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<PostAutoLoginResponse>, response: Response<PostAutoLoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as PostAutoLoginResponse
                    if(result.message == "요청에 성공하였습니다."){
                        sSharedPreferences.edit().putString("X-ACCESS-TOKEN", result.result.jwt).commit()
                        val intent = Intent(this@SplashActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val intent = Intent(this@SplashActivity,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        Log.d("getProfile", "onResponse : Error code ${response.code()}")
                        Log.d("getProfile", "onResponse : Error message ${response.message()}")
                    }
                }
                else{
                    Log.d("getProfile", "onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostAutoLoginResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })
    }
}