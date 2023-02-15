package com.umc.badjang.ScholarshipPage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.umc.badjang.R
import com.umc.badjang.Retrofit.RetrofitManager
import com.umc.badjang.ScholarshipPage.Model.DeleteCommentsDTO
import com.umc.badjang.ScholarshipPage.Model.EditCommentsDTO
import com.umc.badjang.ScholarshipPage.Model.EditCommentsInterface
import kotlinx.android.synthetic.main.dialog_comments_edit.*
import kotlinx.android.synthetic.main.dialog_edit_comments.*

class EditCommentsDialog (context: Context, editCommentsInterface: EditCommentsInterface, curText: String, jwt: String, commentsIdx: Long, user_idx: Int, scholarship_idx: Int): Dialog(context), View.OnClickListener {

    // 현재 로그인 된 사용자 jwt
    private var jwt: String? = jwt

    private var CurText: String = curText
    private var CommentsIdx: Long = commentsIdx
    private var User_idx: Int = user_idx
    private var Scholarship_idx: Int = scholarship_idx
    private var editCommentsInterface: EditCommentsInterface? = null

    // 댓글 수정 dto
    private lateinit var editCommentsPostData: EditCommentsDTO

    init {
        this.editCommentsInterface = editCommentsInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_edit_comments)

        // 배경 투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        comments.setText(CurText)
        btn_input_comments.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {

            // 수정하기 버튼 클릭시
            btn_input_comments -> {
                val curtext: String = comments.text.toString()
                editCommentsPostData = EditCommentsDTO(CommentsIdx, Scholarship_idx, User_idx, curtext)

                EditComments(CommentsIdx, editCommentsPostData)

                this.editCommentsInterface?.editConfirmBtnClicked()

                dismiss()
            }
        }
    }

    // 댓글 수정 (api)
    private fun EditComments(commentsIdx: Long, body: EditCommentsDTO) {
        RetrofitManager.instance.editComments(jwt!!, commentsIdx, body)
    }

}