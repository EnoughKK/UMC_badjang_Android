package com.umc.badjang.LoginPage.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.umc.badjang.LoginPage.LoginActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivityFindIdactivityBinding
import com.umc.badjang.utils.Constants.TAG

class FindIDActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindIdactivityBinding// viewBinding

    private val TAG : String ="FindIDActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩 초기화
        binding = ActivityFindIdactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.FindIDUpBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.FindIDAuthBtn.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
        }


        binding.FindIDPWPage.setOnClickListener {
            startActivity(Intent(this, FindPWActivity::class.java))
        }
    }
}