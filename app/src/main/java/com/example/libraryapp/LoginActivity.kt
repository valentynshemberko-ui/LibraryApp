package com.example.libraryapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val inputLogin = etLogin.text.toString().trim()
            val inputPassword = etPassword.text.toString()

            val sharedPref = getSharedPreferences("LibraryPrefs", Context.MODE_PRIVATE)
            val savedLogin = sharedPref.getString("login", "")
            val savedPassword = sharedPref.getString("password", "")

            if (inputLogin.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Введіть логін та пароль!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputLogin == savedLogin && inputPassword == savedPassword && savedLogin!!.isNotEmpty()) {
                with(sharedPref.edit()) {
                    putBoolean("isAuthorized", true)
                    apply()
                }

                Toast.makeText(this, "Вхід успішний!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Невірний логін або пароль!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}