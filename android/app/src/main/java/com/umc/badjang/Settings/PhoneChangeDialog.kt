package com.umc.badjang.Settings

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.Intent.*
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.umc.badjang.ApplicationClass
import com.umc.badjang.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.umc.badjang.ApplicationClass.Companion.sSharedPreferences
import com.umc.badjang.LoginPage.LoginActivity
import com.umc.badjang.Settings.Logout.models.LogoutRequest
import com.umc.badjang.Settings.Logout.models.LogoutResponse
import com.umc.badjang.Settings.model.PostNameModifyReq
import com.umc.badjang.Settings.model.PostNameModifyResponse
import com.umc.badjang.Settings.model.PostPhoneNumberModifyReq
import com.umc.badjang.databinding.DialogLogoutBinding
import com.umc.badjang.databinding.DialogNamechangeBinding
import com.umc.badjang.databinding.DialogPhonechangeBinding
import com.umc.badjang.databinding.DialogScholarshipLookupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhoneChangeDialog (context:Context) : Dialog(context){
    private lateinit var binding:DialogPhonechangeBinding

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPhonechangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = ApplicationClass.sSharedPreferences.getString("phone",null)
        binding.PhonechangeNumber.append(name.toString())
        initViews()

        binding.PhonechangeCancel.setOnClickListener {
            dismiss()
        }


        binding.PhonechangeOk.setOnClickListener {
            postPhoneNumberModify(PostPhoneNumberModifyReq(user_idx = useridx, user_phone = binding.PhonechangeNumber.text.toString()))
        }

    }

    private fun initViews() = with(binding) {

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }

    private fun postPhoneNumberModify(postPhoneNumberModifyReq: PostPhoneNumberModifyReq){
        val postNameInterface = ApplicationClass.sRetrofit.create(MyInfoRetrofitInterface::class.java)
        postNameInterface.postPhoneNumberModify(postPhoneNumberModifyReq).enqueue(object :
            Callback<PostNameModifyResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<PostNameModifyResponse>, response: Response<PostNameModifyResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as PostNameModifyResponse
                    if(result.message == "요청에 성공하였습니다."){
                        ApplicationClass.sSharedPreferences.edit().putString("phone", null).commit()
                        dismiss()
                        
                    }
                    else{
                        binding.PhonechangeMissnumber.text = result.message.toString()
                        Log.d("postProfile", "onResponse : Error code ${response.code()}")
                        Log.d("postProfile", "onResponse : Error message ${response.message()}")
                    }
                }
                else{
                    Log.d("postProfile", "onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostNameModifyResponse>, t: Throwable) {
                Log.d("postProfile", t.message ?: "통신오류")
            }
        })

    }
}




