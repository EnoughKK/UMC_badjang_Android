package com.umc.badjang.ScholarshipPage.Model

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import com.umc.badjang.R
import kotlinx.android.synthetic.main.dialog_report.*
import kotlinx.android.synthetic.main.dialog_report_confirm.*

class ReportConfirmDialog(context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_report_confirm)

        // 배경 투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 취소버튼 누를시
        btn_cancel.setOnClickListener {
            dismiss()
        }

        // 신고하기 버튼 누를시
        btn_ok.setOnClickListener {

            Toast.makeText(context, "신고가 접수되었습니다.",Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

}