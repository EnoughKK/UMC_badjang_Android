package com.umc.badjang.Settings.Logout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.util.Log
import android.widget.Toast
import com.umc.badjang.ApplicationClass
import com.umc.badjang.ApplicationClass.Companion.QUREY_TEXT
import com.umc.badjang.ApplicationClass.Companion.USER_IDX
import com.umc.badjang.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.umc.badjang.LoginPage.LoginActivity
import com.umc.badjang.Settings.Logout.models.LogoutRequest
import com.umc.badjang.Settings.Logout.models.LogoutResponse
import com.umc.badjang.databinding.DialogLogoutBinding
import com.umc.badjang.databinding.DialogScholarshipLookupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutDialog (context:Context) : Dialog(context){
    private lateinit var binding:DialogLogoutBinding
    private val prefEdit = ApplicationClass.sSharedPreferences.edit()

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

    val logoutRetrofit= ApplicationClass.sRetrofit.create(LogoutRetrofit::class.java)
    val logoutRequest: LogoutRequest = LogoutRequest(user_idx= useridx)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.LogoutCancel.setOnClickListener {
            dismiss()
        }


        binding.LogoutOk.setOnClickListener {
            logout()
            dismiss()
        }

    }

    private fun initViews() = with(binding) {

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }


    private fun logout() {
        //remove가 안 먹히는 것 같으니까 그냥 null값으로 저장
        prefEdit.putString(X_ACCESS_TOKEN,null).apply()
        prefEdit.commit()
        prefEdit.putInt(USER_IDX,0).apply()
        prefEdit.commit()
        prefEdit.putString(QUREY_TEXT,null).apply()
        prefEdit.commit()

        Log.d("logout","마이페이지-설정-로그아웃 jwt = $jwt")
        val clear_jwt=ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
        //초기화 확인
        Log.d("logout","${clear_jwt}")

        Log.d("로그아웃", useridx.toString()+"-------"+jwt)

        logoutRetrofit.requestLogout(jwt.toString(), logoutRequest).enqueue(object :
            Callback<LogoutResponse> {
            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                Log.d("로그아웃 서버", "${t.localizedMessage}")
            }

            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                Log.d("로그아웃 서버", response.body().toString())
                when(response.body()!!.code) {
                    1000->{
                        val intent = Intent(context,LoginActivity::class.java)
                        //지금 까지 쌓여있는 모든 액티비티 지우기
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK and FLAG_ACTIVITY_CLEAR_TASK and FLAG_ACTIVITY_CLEAR_TOP)
                        context.startActivity(intent)
                    }
                    else->{
                        Log.e("로그아웃 실패","${response.message()}")
                        binding.logoutText.text="로그아웃을 실패하셨습니다!"
                    }
                }
            }
        })
    }

}




