package com.umc.badjang.LoginPage.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivityChangePwactvityBinding
import com.umc.badjang.databinding.ActivityPrivacyBinding

class ChangePWActvity : AppCompatActivity() {

    private lateinit var binding : ActivityChangePwactvityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityChangePwactvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}