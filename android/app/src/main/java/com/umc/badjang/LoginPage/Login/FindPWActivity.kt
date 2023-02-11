package com.umc.badjang.LoginPage.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.badjang.LoginPage.LoginActivity
import com.umc.badjang.databinding.ActivityFindPwactivityBinding

class FindPWActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindPwactivityBinding// viewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩 초기화
        binding = ActivityFindPwactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.FindPWUpBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.FindPWIDpage.setOnClickListener {
            startActivity(Intent(this, FindIDActivity::class.java))
        }
    }
}