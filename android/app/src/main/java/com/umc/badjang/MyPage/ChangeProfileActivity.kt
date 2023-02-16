package com.umc.badjang.MyPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MyPage.Model.MyProfileRes
import com.umc.badjang.MyPage.Model.PostProfileModifyReq
import com.umc.badjang.MyPage.Model.PostUserInfoModify
import com.umc.badjang.databinding.ActivityChangeProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileBinding // viewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var userName = intent.getStringExtra("name").toString()
        binding.changeProfileEtName.append(userName)
        binding.changeProfileTvNameCount.text = "${binding.changeProfileEtName.text.length}/20"
        binding.changeProfileEtName.addTextChangedListener {
            binding.changeProfileTvNameCount.text = "${binding.changeProfileEtName.text.length}/20"
        }

        binding.changeProfileBtnChange.setOnClickListener {
            var idx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)
            println("idx : $idx")
            postProfileModify(PostProfileModifyReq(user_idx = idx, user_name = binding.changeProfileEtName.text.toString()))
        }

        binding.changeProfileIbPrev.setOnClickListener {
            finish()
        }
    }
    private fun postProfileModify(postProfileModifyReq: PostProfileModifyReq){
        val scholarshipInterface = ApplicationClass.sRetrofit.create(MyProfileRetrofitInterface::class.java)
        scholarshipInterface.postProfileModify(postProfileModifyReq).enqueue(object : Callback<PostUserInfoModify> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<PostUserInfoModify>, response: Response<PostUserInfoModify>) {
                if (response.isSuccessful) {
                    val result = response.body() as PostUserInfoModify
                    if(result.message == "요청에 성공하였습니다."){

                        Toast.makeText(applicationContext,result.result.toString(),Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,result.message.toString(),Toast.LENGTH_SHORT).show()
                        Log.d("postProfile", "onResponse : Error code ${response.code()}")
                        Log.d("postProfile", "onResponse : Error message ${response.message()}")
                    }
                }
                else{
                    Log.d("postProfile", "onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostUserInfoModify>, t: Throwable) {
                Log.d("postProfile", t.message ?: "통신오류")
            }
        })

    }
}