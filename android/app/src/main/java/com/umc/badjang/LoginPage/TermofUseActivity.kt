package com.umc.badjang.LoginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivityKakaoWebViewBinding
import com.umc.badjang.databinding.ActivityTermofuseBinding

class TermofUseActivity : AppCompatActivity() {
    private lateinit var binding :ActivityTermofuseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermofuseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding.webViewTerm) {
            settings.javaScriptEnabled = true
            //webViewClient = WebViewClient()
            loadUrl(
                "file:///assets/badjang_privacy.html"
            )
            android.util.Log.d("서비스 이용약관","loadUrl")
        }

        binding.UseUpBtn.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}

/*
        //1.url
        webView.loadUrl("https://m.naver.com")

        //2.html코드 작성
      	 val html = "<html><head><meta charset=\"UTF-8\"></head><body>Hello World! 안녕하세요!</body></html>"
        webView.loadData(html, "text/html", "UTF-8")

        //3.html 문서 호출
        webView.loadUrl("file:///assets/badjang_privacy.html")//html파일의경로통해 해당 파일 호출

        webView.settings.javaScriptEnabled=true

        //webView.webChromeClient = WebChromeClient()
*/
