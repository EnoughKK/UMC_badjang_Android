package com.umc.badjang.LoginPage.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.badjang.LoginPage.LoginActivity
import com.umc.badjang.databinding.ActivityFindIdactivityBinding

class FindIDActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindIdactivityBinding// viewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩 초기화
        binding = ActivityFindIdactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.FindIDUpBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.FindIDPWPage.setOnClickListener {
            startActivity(Intent(this, FindPWActivity::class.java))
        }
    }
}