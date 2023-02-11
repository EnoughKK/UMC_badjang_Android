package com.umc.badjang.LoginPage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.umc.badjang.databinding.DialogTermtodisagreeBinding

class MyDialog(private val text : String) : DialogFragment() {
    private var _binding : DialogTermtodisagreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= DialogTermtodisagreeBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.termtodisagreeText.text = text
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