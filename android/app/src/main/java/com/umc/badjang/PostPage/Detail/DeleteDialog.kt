package com.umc.badjang.PostPage.Detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.umc.badjang.ApplicationClass
import com.umc.badjang.PostPage.Detail.Model.PostDeletePostResponse
import com.umc.badjang.R
import com.umc.badjang.databinding.DialogPostDeleteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeleteDialog (context:Context) : Dialog(context){
    private lateinit var binding:DialogPostDeleteBinding
    private val prefEdit = ApplicationClass.sSharedPreferences.edit()

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)
    val post_idx = ApplicationClass.bSharedPreferences.getInt("post_idx", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPostDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.deleteCancel.setOnClickListener {
            ApplicationClass.bSharedPreferences.edit().putString("delete_content","").commit()
            dismiss()
        }


        binding.deleteOk.setOnClickListener {
            getDeletePost(post_idx, useridx)
        }

    }

    private fun initViews() = with(binding) {

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }


    private fun getDeletePost(post_idx: Int, user_idx: Int){
        //Log.d("postScholarship", "호출은 된다.")
        val getOnePostInterface = ApplicationClass.sRetrofit.create(DetailPostRetrofitInterface::class.java)
        getOnePostInterface.postDeletePost(post_idx, user_idx).enqueue(object :
            Callback<PostDeletePostResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<PostDeletePostResponse>, response: Response<PostDeletePostResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as PostDeletePostResponse
                    if(result.message == "요청에 성공하였습니다."){
                        ApplicationClass.bSharedPreferences.edit().putString("delete_content","삭제").commit()
                        dismiss()
                        val view1 = layoutInflater.inflate(R.layout.toastmsg_layout,null)

                        var text : TextView? = view1.findViewById(R.id.ChangePW_toast)
                        text?.text = "게시글을 삭제하였습니다."
                        var toast = Toast(context)
                        toast.view = view1
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                    }
                    else{
                        Log.d("getProfile", "onResponse : Error code ${response.code()}")
                        Log.d("getProfile", "onResponse : Error message ${response.message()}")
                    }
                }
                else{
                    Log.d("getProfile", "onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<PostDeletePostResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }

}




