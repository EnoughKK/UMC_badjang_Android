package com.umc.badjang.LoginPage.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.umc.badjang.databinding.ActivityPrivacyBinding


class PrivacyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPrivacyBinding

    var activity2: PrivacyActivity?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity2=this@PrivacyActivity

        with(binding.webViewPrivacy) {
            settings.javaScriptEnabled = true

            loadUrl(
                "file:///android_asset/badjang_privacy.html"
            )
            Log.d("개인정보 이용약관","loadUrl")
        }

        binding.PrivacyUpBtn.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.PrivacyBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            //동의하기를 누르면 회원가입 창에서도 체크 표시된다.
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("Privacy_Btn", 1)
            startActivity(intent)
            finish()
        }
    }
}

