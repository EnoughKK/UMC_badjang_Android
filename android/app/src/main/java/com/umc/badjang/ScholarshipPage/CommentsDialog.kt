package com.umc.badjang.ScholarshipPage

import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.umc.badjang.databinding.DialogCommentsEditBinding

class CommentsDialog(val context : AppCompatActivity) {

    private lateinit var viewBinding: DialogCommentsEditBinding
    private val dlg = Dialog(context)

    fun show(content : String) {
        viewBinding = DialogCommentsEditBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(viewBinding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)

    }

}