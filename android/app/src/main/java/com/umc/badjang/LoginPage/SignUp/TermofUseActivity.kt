package com.umc.badjang.LoginPage.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.umc.badjang.databinding.ActivityTermofuseBinding

class TermofUseActivity : AppCompatActivity() {
    private lateinit var binding :ActivityTermofuseBinding

    var activity1: TermofUseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermofuseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity1=this@TermofUseActivity

        with(binding.webViewTerm) {
            settings.javaScriptEnabled = true

            loadUrl(
                "file:///android_asset/badjang_termofuse.html")

            Log.d("서비스 이용약관","loadUrl")
        }

        binding.UseUpBtn.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }


        binding.UseBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            //동의하기를 누르면 회원가입 창에서도 체크 표시된다.
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("Term_Btn", 1)
            startActivity(intent)
            finish()
        }
    }
}
