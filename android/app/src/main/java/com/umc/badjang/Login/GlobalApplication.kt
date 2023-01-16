package com.umc.badjang.Login

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "b31ceafa89bebeeb560346e90f07ea91")
    }
}