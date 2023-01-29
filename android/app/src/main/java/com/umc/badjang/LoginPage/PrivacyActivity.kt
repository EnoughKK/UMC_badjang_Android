package com.umc.badjang.LoginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivityPrivacyBinding
import com.umc.badjang.databinding.ActivityTermofuseBinding

class PrivacyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPrivacyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding.webViewPrivacy) {
            settings.javaScriptEnabled = true
            //webViewClient = WebViewClient()
            loadUrl(
                "file:///assets/badjang_termofuse.html"
            )
            android.util.Log.d("서비스 이용약관","loadUrl")
        }

        binding.PrivacyUpBtn.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}