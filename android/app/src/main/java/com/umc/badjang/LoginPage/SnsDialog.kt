package com.umc.badjang.LoginPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.umc.badjang.databinding.DialogSnsInfoBinding
import com.umc.badjang.databinding.DialogTermtodisagreeBinding

class SnsDialog(private val text : String) : DialogFragment() {
    private var _binding : DialogSnsInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= DialogSnsInfoBinding.inflate(inflater,container,false)
        val view = binding.root
        var snsname:String = binding.snsName.text.toString()
        var snsbirthdate:String =binding.snsBirthDate.text.toString()
        var snsphone:String = binding.snsPhone.text.toString()

        //레이아웃 배경을 투명하게 해줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        binding.DisagreeBtn.setOnClickListener {
            buttonClickListener.onButtonClicked()
            dismiss()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    interface OnButtonClickListener {
        fun onButtonClicked()
    }
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener){
        this.buttonClickListener = buttonClickListener
    }
    private lateinit var buttonClickListener: OnButtonClickListener


}