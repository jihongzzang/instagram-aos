package com.example.mycrudapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class InstaSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_splash)

        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "empty")
        when (token) {
            "empty" -> {
                Handler(Looper.getMainLooper()).postDelayed(
                    { startActivity(Intent(this, InstaLoginActivity::class.java)) }, 1000
                )
            }

            else -> {
                Handler(Looper.getMainLooper()).postDelayed(
                    { startActivity(Intent(this, InstaMainActivity::class.java)) }, 1000
                )
            }
        }
    }
}