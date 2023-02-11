package com.umc.badjang.Settings.Myinfo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Settings.MyInfoFragment
import com.umc.badjang.Settings.Myinfo.models.MyinfoRequest
import com.umc.badjang.Settings.Myinfo.models.MyinfoResponse
import com.umc.badjang.databinding.DialogPhonechangeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyinfoPhoneDialog (context: Context) : Dialog(context) {
    private lateinit var binding: DialogPhonechangeBinding
    private val prefEdit = ApplicationClass.sSharedPreferences.edit()

    // 프래그먼트 전환을 위해
    var activity: MainActivity? = null

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPhonechangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.PhonechangeCancel.setOnClickListener {
            dismiss()
        }


        binding.PhonechangeOk.setOnClickListener {
            changephone()
            dismiss()
        }

    }


    private fun initViews() = with(binding) {

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }


    private fun changephone() {

        val changephone = binding.PhonechangeNumber.text.toString()

        val myinfoRetrofit = ApplicationClass.sRetrofit.create(MyinfoRetrofit::class.java)
        val myinfoRequest: MyinfoRequest = MyinfoRequest(
            user_idx = useridx,
            user_name = null,
            user_phone = changephone
        )

        myinfoRetrofit.requestMyinfo(jwt.toString(), myinfoRequest).enqueue(object :
            Callback<MyinfoResponse> {
            override fun onFailure(call: Call<MyinfoResponse>, t: Throwable) {
                Log.d("전번변경 서버", "${t.localizedMessage}")
            }

            override fun onResponse(call: Call<MyinfoResponse>, response: Response<MyinfoResponse>) {
                Log.d("전번변경 서버", response.body().toString())
                when(response.body()!!.code) {
                    1000->{
                        val bundle:Bundle = Bundle()
                        bundle.putString("changephone",changephone)

                        val myInfoFragment = MyInfoFragment()
                        myInfoFragment.arguments=bundle

                        val fragmentManager = (activity as MainActivity).supportFragmentManager

                        val transaction =fragmentManager.beginTransaction()
                        transaction.add(R.id.my_info_tv_phone_number,myInfoFragment)
                        transaction.commit()
                        Log.d("fragment로 전번 전송","${transaction}")
                    }
                    else->{
                        Log.e("전번변경 실패","${response.message()}")
                        binding.PhonechangeMissnumber.visibility= View.VISIBLE
                        binding.PhonechangeMissnumber.text="${response.message()}"
                    }
                }
            }
        })
    }

}
