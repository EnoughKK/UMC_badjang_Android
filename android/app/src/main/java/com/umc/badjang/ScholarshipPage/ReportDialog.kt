package com.umc.badjang.ScholarshipPage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.umc.badjang.R
import com.umc.badjang.ScholarshipPage.Model.ReportConfirmDialog
import kotlinx.android.synthetic.main.dialog_report.*

class ReportDialog(context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_report)

        // 배경 투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btn_report.setOnClickListener {

            // 신고하기 확인 다이얼로그
            val myCustomDialog = ReportConfirmDialog(context)
            myCustomDialog.show()
            dismiss()
        }
    }

}