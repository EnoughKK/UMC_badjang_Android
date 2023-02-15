package com.umc.badjang.ScholarshipPage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.umc.badjang.R
import com.umc.badjang.ScholarshipPage.Model.CommentsDialogInterface
import com.umc.badjang.databinding.DialogCommentsEditBinding
import kotlinx.android.synthetic.main.dialog_comments_edit.*

class CommentsDialog(context: Context, commentsDialogInterface: CommentsDialogInterface): Dialog(context), View.OnClickListener {

    private var commentsDialogInterface: CommentsDialogInterface? = null

    init {
        this.commentsDialogInterface = commentsDialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_comments_edit)

        // 배경 투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btn_edit.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {

            // 수정하기 버튼 클릭시
            btn_edit -> {
                this.commentsDialogInterface?.editBtnClicked()
                dismiss()
            }

            // 삭제하기 버튼 클릭시
            btn_delete -> {
                this.commentsDialogInterface?.deleteClicked()
                dismiss()
            }
        }
    }

}