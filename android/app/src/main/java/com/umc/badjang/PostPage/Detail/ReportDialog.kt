package com.umc.badjang.PostPage.Detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent.*
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.umc.badjang.ApplicationClass
import com.umc.badjang.PostPage.Detail.Model.PostDeletePostResponse
import com.umc.badjang.R
import com.umc.badjang.databinding.DialogPostDeleteBinding
import com.umc.badjang.databinding.DialogPostReportBinding
import com.umc.badjang.databinding.ToastmsgLayoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReportDialog (context:Context) : Dialog(context){
    private lateinit var binding:DialogPostReportBinding
    private val prefEdit = ApplicationClass.sSharedPreferences.edit()

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)
    val post_idx = ApplicationClass.bSharedPreferences.getInt("post_idx", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPostReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.reportCancel.setOnClickListener {
            dismiss()
        }


        binding.reportOk.setOnClickListener {
            //getOnePost(post_idx, useridx)
            dismiss()
            val view1 = layoutInflater.inflate(R.layout.toastmsg_layout,null)

            var text : TextView? = view1.findViewById(R.id.ChangePW_toast)
            text?.text = "이 글을 신고하였습니다."
            var toast = Toast(context)
            toast.view = view1
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }

    }

    private fun initViews() = with(binding) {

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }

}