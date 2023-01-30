package com.umc.badjang.LoginPage.Kakao
/*
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.umc.badjang.BuildConfig
import com.umc.badjang.LoginPage.models.KakaoOauthRequest
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentKakaoWebViewBinding


class KakaoWebViewFragment : Fragment() {

    lateinit var binding: FragmentKakaoWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKakaoWebViewBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kakaoWebView.run {
            webViewClient = CustomWebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(
                "https://kauth.kakao.com/oauth/authorize?client_id=" +"b31ceafa89bebeeb560346e90f07ea91" +
                        "&redirect_uri=http://localhost:9000/oauth/kakao&response_type=code"
            )
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        private fun checkUrl(request: WebResourceRequest?) {
            val url = request?.url ?: return
            val code = url.getQueryParameter(KAKAO_OAUTH_CODE_PARAM_KEY)
            Log.d("카카오","${url}")

            if (url.scheme == KAKAO_OAUTH_REDIRECTION_SCHEME &&
                url.host == KAKAO_OAUTH_REDIRECTION_HOST &&
                code != null
            ) {
                Log.i("test code", " $code")
                //.getKakaoToken(KakaoOauthRequest(code.toString()))
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            checkUrl(request)
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    companion object {
        private const val KAKAO_OAUTH_REDIRECTION_SCHEME = "http"
        private const val KAKAO_OAUTH_REDIRECTION_HOST = "9000"
        private const val KAKAO_OAUTH_CODE_PARAM_KEY = "code"
    }

    */
class KakaoWebViewFragment{}