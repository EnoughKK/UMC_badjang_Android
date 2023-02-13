package com.umc.badjang.Settings.Myinfo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat.startActivityForResult
import com.umc.badjang.ApplicationClass
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.Settings.Myinfo.models.MyinfoRequest
import com.umc.badjang.Settings.Myinfo.models.MyinfoResponse
import com.umc.badjang.databinding.DialogNamechangeBinding
import kotlinx.android.synthetic.main.dialog_namechange.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyinfoNameDialog (context: Context) : Dialog(context) {
    private lateinit var binding: DialogNamechangeBinding
    private val prefEdit = ApplicationClass.sSharedPreferences.edit()

    // 프래그먼트 전환을 위해
    var activity: MainActivity? = null

    val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)

    //user_idx 불러옴
    val useridx = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogNamechangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


        binding.NamechangeCancel.setOnClickListener {
            dismiss()
        }


        binding.NamechangeOk.setOnClickListener {
            changename()
            dismiss()
        }
    }


    private fun initViews() = with(binding) {

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }


    private fun changename() {

        val changename:String = binding.NamechangeName.text.toString()

        val myinfoRetrofit = ApplicationClass.sRetrofit.create(MyinfoRetrofit::class.java)
        val myinfoRequest: MyinfoRequest = MyinfoRequest(
            user_idx = useridx,
            user_name = changename,
            user_phone = null
        )

        myinfoRetrofit.requestMyinfo(jwt.toString(), myinfoRequest).enqueue(object :
            Callback<MyinfoResponse> {
            override fun onFailure(call: Call<MyinfoResponse>, t: Throwable) {
                Log.d("이름변경 서버", "${t.localizedMessage}")
            }

            override fun onResponse(call: Call<MyinfoResponse>, response: Response<MyinfoResponse>) {
                Log.d("이름변경 서버", response.body().toString())
                when(response.body()!!.code) {
                    1000->{
                        var intent = Intent(context,MyinfoNameDialog::class.java)
                        Log.d("fragment로 이름 전송","${changename}}")

                    }
                    else->{
                        Log.e("이름변경 실패","${response.message()}")
                        binding.NamechangeNameError.visibility= View.VISIBLE
                        binding.NamechangeNameError.text="${response.message()}"
                    }
                }


            }
        })
    }

}
