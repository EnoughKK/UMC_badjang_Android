package com.umc.badjang.LoginPage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.umc.badjang.databinding.ActivityKakaoWebViewBinding

class KakaoWebViewActivity: AppCompatActivity() {
    private lateinit var binding:ActivityKakaoWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
 */

        with(binding.webView) {
            settings.javaScriptEnabled = true
            //webViewClient = WebViewClient()
            loadUrl(
                "https://kauth.kakao.com/oauth/authorize?client_id=b31ceafa89bebeeb560346e90f07ea91&redirect_uri=https://prod.badjang2023.shop/oauth/kakao&response_type=code"
            )
            Log.d("로그인","loadUrl")
        }
    }
}

