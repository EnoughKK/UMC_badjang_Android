package com.umc.badjang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.badjang.databinding.ActivityLoginBinding
import com.umc.badjang.databinding.ActivityLogoBinding

class LogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLogoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}