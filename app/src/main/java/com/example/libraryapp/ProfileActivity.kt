package com.example.libraryapp

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {

    private lateinit var ivAvatar: ImageView
    private lateinit var tvProfileDob: TextView
    private var selectedDate = ""
    private var selectedBitmap: Bitmap? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val inputStream = contentResolver.openInputStream(it)
            selectedBitmap = BitmapFactory.decodeStream(inputStream)
            ivAvatar.setImageBitmap(selectedBitmap)
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            selectedBitmap = it
            ivAvatar.setImageBitmap(selectedBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ivAvatar = findViewById(R.id.ivAvatar)
        val btnChangePhoto = findViewById<Button>(R.id.btnChangePhoto)
        val etProfileName = findViewById<EditText>(R.id.etProfileName)
        val etProfileEmail = findViewById<EditText>(R.id.etProfileEmail)
        tvProfileDob = findViewById(R.id.tvProfileDob)
        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile)

        try {
            val file = File(filesDir, "avatar.png")
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                ivAvatar.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val sharedPref = getSharedPreferences("LibraryPrefs", Context.MODE_PRIVATE)

        etProfileName.setText(sharedPref.getString("name", ""))
        etProfileEmail.setText(sharedPref.getString("email", ""))

        val savedDob = sharedPref.getString("dob", "")
        if (!savedDob.isNullOrEmpty()) {
            selectedDate = savedDob
            tvProfileDob.text = savedDob
        } else {
            tvProfileDob.text = "📅 Оберіть дату народження"
        }

        tvProfileDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvProfileDob.text = selectedDate
            }, year, month, day).show()
        }

        btnChangePhoto.setOnClickListener {
            val options = arrayOf("Камера", "Галерея")
            AlertDialog.Builder(this)
                .setTitle("Оберіть джерело фото")
                .setItems(options) { _, which ->
                    if (which == 0) {
                        cameraLauncher.launch(null)
                    } else {
                        galleryLauncher.launch("image/*")
                    }
                }
                .show()
        }

        btnSaveProfile.setOnClickListener {
            val newName = etProfileName.text.toString().trim()
            val newEmail = etProfileEmail.text.toString().trim()

            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Заповніть всі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            selectedBitmap?.let { bitmap ->
                try {
                    val file = File(filesDir, "avatar.png")
                    val outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            with(sharedPref.edit()) {
                putString("name", newName)
                putString("email", newEmail)
                putString("dob", selectedDate)
                apply()
            }

            Toast.makeText(this, "Дані успішно оновлено!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}