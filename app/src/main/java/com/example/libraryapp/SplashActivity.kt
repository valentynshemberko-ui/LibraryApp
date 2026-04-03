package com.example.libraryapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("LibraryPrefs", Context.MODE_PRIVATE)
        val isAuthorized = sharedPref.getBoolean("isAuthorized", false)

        if (isAuthorized) {
            startActivity(Intent(this, MenuActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }
}