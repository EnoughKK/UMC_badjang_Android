package com.umc.badjang.PostPage.Detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.PostPage.Detail.Model.PostDeletePostResponse
import com.umc.badjang.PostPage.Detail.Model.PostSchoolCommentResponse
import com.umc.badjang.R
import com.umc.badjang.databinding.DialogCommentDeleteBinding
import com.umc.badjang.databinding.DialogPostDeleteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SchoolCommentDeleteDialog (context:Context, schoolNameIdx : Int, post_idx:Int, commentIdx: Int) : Dialog(context){
    private lateinit var binding:DialogCommentDeleteBinding
    private val prefEdit = ApplicationClass.sSharedPreferences.edit()

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)
    val post_idx = ApplicationClass.bSharedPreferences.getInt("post_idx", 0)
    val comment_idx = ApplicationClass.bSharedPreferences.getInt("comment_idx", 0)
    val back_s_idx = schoolNameIdx
    val back_p_idx = post_idx
    val back_c_idx = commentIdx
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCommentDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.commentDeleteCancel.setOnClickListener {
            ApplicationClass.bSharedPreferences.edit().putString("delete_school_comment_content","").commit()
            dismiss()
        }


        binding.commentDeleteOk.setOnClickListener {
            deleteSchoolComment(back_s_idx,back_p_idx, back_c_idx)
            dismiss()
        }

    }

    private fun initViews() = with(binding) {

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }
    private fun getDeleteComment(comment_idx:Int, user_idx: Int, post_idx: Int){
        //Log.d("postScholarship", "호출은 된다.")
        val getOnePostInterface = ApplicationClass.sRetrofit.create(DetailPostRetrofitInterface::class.java)
        getOnePostInterface.postDeleteComment(comment_idx, user_idx, post_idx).enqueue(object :
            Callback<PostDeletePostResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<PostDeletePostResponse>, response: Response<PostDeletePostResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as PostDeletePostResponse
                    if(result.message == "요청에 성공하였습니다."){
                        ApplicationClass.bSharedPreferences.edit().putString("delete_comment_content","삭제").commit()

                        val view1 = layoutInflater.inflate(R.layout.toastmsg_layout,null)

                        var text : TextView? = view1.findViewById(R.id.ChangePW_toast)
                        text?.text = "댓글을 삭제하였습니다."
                        var toast = Toast(context)
                        toast.view = view1
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                        dismiss()

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
    private fun deleteSchoolComment(schoolNameIdx: Int, post_idx: Int, commentIdx: Int){
        //Log.d("postScholarship", "호출은 된다.")
        val getCommentsInterface = ApplicationClass.sRetrofit.create(DetailPostRetrofitInterface::class.java)
        getCommentsInterface.deleteSchoolComment(schoolNameIdx, post_idx, commentIdx).enqueue(object :
            Callback<PostSchoolCommentResponse> {
            @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
            override fun onResponse(call: Call<PostSchoolCommentResponse>, response: Response<PostSchoolCommentResponse>) {
                if (response.isSuccessful) {
                    val result = response.body() as PostSchoolCommentResponse
                    if(result.message == "댓글이 삭제되었습니다."){
                        ApplicationClass.bSharedPreferences.edit().putString("delete_school_comment_content","삭제").commit()

                        val view1 = layoutInflater.inflate(R.layout.toastmsg_layout,null)

                        var text : TextView? = view1.findViewById(R.id.ChangePW_toast)
                        text?.text = "댓글을 삭제하였습니다."
                        var toast = Toast(context)
                        toast.view = view1
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                        dismiss()
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
            override fun onFailure(call: Call<PostSchoolCommentResponse>, t: Throwable) {
                Log.d("getProfile", t.message ?: "통신오류")
            }
        })

    }

}




