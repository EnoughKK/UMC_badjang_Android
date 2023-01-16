package com.umc.badjang.Login

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.auth.*

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.ktx.storage


class GlobalApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        var email: String? = null
        lateinit var db: FirebaseFirestore
        //lateinit var storage: FirebaseStorage
        fun checkAuth(): Boolean {
            var currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                currentUser.isEmailVerified
            } ?: let {
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        //kakao SDK 초기화
        KakaoSdk.init(this, "b31ceafa89bebeeb560346e90f07ea91")
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        //storage = Firebase.storage
    }
}

