package com.umc.badjang.LoginPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.umc.badjang.R

class TermofUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termofuse)
        val webView = findViewById<WebView>(R.id.privacy_webview)
/*
        //1.url
        webView.loadUrl("https://m.naver.com")

        //2.html코드 작성
      	 val html = "<html><head><meta charset=\"UTF-8\"></head><body>Hello World! 안녕하세요!</body></html>"
        webView.loadData(html, "text/html", "UTF-8")
*/
        //3.html 문서 호출
        webView.loadUrl("file:///assets/badjang_privacy.html")//html파일의경로통해 해당 파일 호출

        webView.settings.javaScriptEnabled=true

        webView.webChromeClient = WebChromeClient()

    }
}