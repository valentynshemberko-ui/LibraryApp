package com.example.libraryapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val btnManageBooks = findViewById<Button>(R.id.btnManageBooks)
        val btnAuthorsInfo = findViewById<Button>(R.id.btnAuthorsInfo)
        val btnProfile = findViewById<Button>(R.id.btnProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val sharedPref = getSharedPreferences("LibraryPrefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", "")
        val surname = sharedPref.getString("surname", "")

        tvGreeting.text = "Привіт, $name $surname!"

        btnManageBooks.setOnClickListener {
            startActivity(Intent(this, ManageBooksActivity::class.java))
        }

        btnAuthorsInfo.setOnClickListener {
            startActivity(Intent(this, AuthorsActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnLogout.setOnClickListener {
            with(sharedPref.edit()) {
                putBoolean("isAuthorized", false)
                apply()
            }
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}