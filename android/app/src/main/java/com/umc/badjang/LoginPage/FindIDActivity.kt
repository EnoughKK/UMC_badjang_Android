package com.umc.badjang.LoginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivityFindIdactivityBinding
import com.umc.badjang.databinding.ActivityFindPwactivityBinding

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
    }
}