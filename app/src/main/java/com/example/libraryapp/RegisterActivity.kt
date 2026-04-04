package com.example.libraryapp

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etSurname = findViewById<EditText>(R.id.etSurname)
        val tvDob = findViewById<TextView>(R.id.tvDob)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etRepeatPassword = findViewById<EditText>(R.id.etRepeatPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        tvDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvDob.text = "Дата народження: $selectedDate"
            }, year, month, day)
            datePickerDialog.show()
        }

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val surname = etSurname.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val login = etLogin.text.toString().trim()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(this, "Невірний формат Email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Пароль має бути мінімум 6 символів!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(this, "Паролі не співпадають!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("LibraryPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("name", name)
                putString("surname", surname)
                putString("dob", selectedDate)
                putString("email", email)
                putString("login", login)
                putString("password", password)
                apply()
            }

            Toast.makeText(this, "Реєстрація успішна!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}