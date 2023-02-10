package com.umc.badjang.LoginPage

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.databinding.DialogSnsInfoBinding
import com.umc.badjang.databinding.DialogTermtodisagreeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class SnsDialog(private val text : String) : DialogFragment() {
    private var _binding : DialogSnsInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var string : String  //알람동의를 위한
    private var count by Delegates.notNull<Int>()

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN,null)
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX,0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSnsInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.snsTitle.text = text
        //레이아웃 배경을 투명하게 해줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        count=0

        binding.snsAlarmDisagree.setOnClickListener {
            binding.snsAlarmAgree.visibility = View.VISIBLE
            binding.snsAlarmDisagree.visibility = View.INVISIBLE
            count = 1
        }

        binding.snsAlarmAgree.setOnClickListener {
            binding.snsAlarmDisagree.visibility = View.VISIBLE
            binding.snsAlarmDisagree.visibility = View.INVISIBLE
            count = 0
        }


        binding.snsagreeBtn.setOnClickListener {
            var name: String = binding.snsName.text.toString()
            var birth: String = binding.snsBirthDate.text.toString()
            var phone: String = binding.snsPhone.text.toString()


            if (count == 1) {
                string = "Y"
            }
            if (count == 0) {
                string = "N"
            }

            var push_yn=string.toString()

            //오류 검사
            if (!validName() or !validBirthdate() or !validPhone()) {

                return@setOnClickListener
            }


            val snsRetrofit = ApplicationClass.sRetrofit.create(SnsRetrofit::class.java)
            val snsRequest: SNSRequest = SNSRequest(
                user_idx=useridx,
                user_name = name,
                user_birth = birth,
                user_phone = phone,
                user_push_yn = push_yn
            )

            snsRetrofit.requestSNS(jwt.toString(), snsRequest).enqueue(object :
                Callback<SNSResponse> {
                override fun onFailure(call: Call<SNSResponse>, t: Throwable) {
                    Log.d("소셜 정보 추가 서버 실패", "${t.localizedMessage}")
                }

                override fun onResponse(call: Call<SNSResponse>, response: Response<SNSResponse>) {
                    Log.d("소셜 정보추가 서버", response.body().toString())
                    when (response.body()!!.code) {
                        1000 -> {
                            buttonClickListener.onButtonClicked()
                            dismiss()
                            val intent = Intent(requireContext(),MainActivity::class.java)
                            //지금 까지 쌓여있는 모든 액티비티 지우기
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }
                        else -> {
                            Log.e("소셜 정보 추가 실패", "${response.message()}")
                            Toast.makeText(
                                ApplicationClass.instance,
                                "${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.snsName.setText(null)
                            binding.snsPhone.setText(null)
                            binding.snsBirthDate.setText(null)
                        }
                    }


                }
            })
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




private fun validName():Boolean {
    val value: String = binding.snsName.text.toString()

    return if (value.isBlank()) {
        binding.snsMissName.text = "이름을 입력해주세요."
        binding.snsMissName.visibility = View.VISIBLE
        false
    } else if (value.length < 2 || value.length > 20) {
        binding.snsMissName.text = "이름은 2~20자 사이로 입력해주세요"
        binding.snsMissName.visibility = View.VISIBLE
        false
    } else {
        binding.snsName.isEnabled = false
        binding.snsMissName.visibility = View.INVISIBLE
        binding.snsName.error = null
        true
    }
}

private fun validBirthdate():Boolean{
    val value: String = binding.snsBirthDate.text.toString()

    return if (value.isBlank()) {
        binding.snsMissBirthDate.text = "생년월일을 입력해 주세요."
        binding.snsMissBirthDate.visibility = View.VISIBLE
        false
    } else if (value.length ==8) {
        binding.snsBirthDate.isEnabled = false
        binding.snsMissBirthDate.visibility = View.INVISIBLE
        binding.snsBirthDate.error = null
        true
    } else {
        binding.snsMissBirthDate.text = "생년월일 형식에 맞게 입력해주세요"
        binding.snsMissBirthDate.visibility = View.VISIBLE
        false
    }
}


private fun validPhone():Boolean{

    val value: String = binding.snsPhone.text.toString()
    val phonePattern = ".*[0-9].*"

    return if (value.isBlank()) {
        binding.snsMissPhone.text = "전화번호를 입력해 주세요."
        binding.snsMissPhone.visibility = View.VISIBLE
        false
    } else if (!value.matches(phonePattern.toRegex())||value.length != 11) {
        binding.snsMissPhone.text = "전화번호 형식에 맞게 입력해주세요"
        binding.snsMissPhone.visibility = View.VISIBLE
        false
    } else {
        binding.snsPhone.isEnabled = false
        binding.snsMissPhone.visibility = View.INVISIBLE
        binding.snsPhone.error = null
        true
    }
}

}



