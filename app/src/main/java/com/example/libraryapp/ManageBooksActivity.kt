package com.example.libraryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManageBooksActivity : AppCompatActivity() {

    private val booksList = mutableListOf(
        "1984 - Джордж Оруелл", "Володар перснів - Дж.Р.Р. Толкін", "Гаррі Поттер - Дж.К. Роулінг",
        "Маленький принц - Антуан де Сент-Екзюпері", "Гордість і упередження - Джейн Остін",
        "Великий Гетсбі - Ф. Скотт Фіцджеральд", "Убити пересмішника - Гарпер Лі",
        "Алхімік - Пауло Коельйо", "Сто років самотності - Габріель Гарсія Маркес",
        "Кобзар - Тарас Шевченко"
    )

    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_books)

        val rvBooks = findViewById<RecyclerView>(R.id.rvBooks)
        val btnAddBook = findViewById<Button>(R.id.btnAddBook)

        adapter = BookAdapter(booksList)
        rvBooks.layoutManager = LinearLayoutManager(this)
        rvBooks.adapter = adapter

        btnAddBook.setOnClickListener {
            showBookDialog(-1)
        }
    }

    private fun showBookDialog(position: Int) {
        val input = EditText(this)
        if (position >= 0) {
            input.setText(booksList[position])
        }

        AlertDialog.Builder(this)
            .setTitle(if (position >= 0) "Редагувати книгу" else "Додати нову книгу")
            .setView(input)
            .setPositiveButton("Зберегти") { _, _ ->
                val newText = input.text.toString()
                if (newText.isNotEmpty()) {
                    if (position >= 0) {
                        booksList[position] = newText
                        adapter.notifyItemChanged(position)
                    } else {
                        booksList.add(newText)
                        adapter.notifyItemInserted(booksList.size - 1)
                    }
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    inner class BookAdapter(private val items: MutableList<String>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

        inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvBookName: TextView = itemView.findViewById(R.id.tvBookName)
            val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
            return BookViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
            holder.tvBookName.text = items[position]

            holder.btnDelete.setOnClickListener {
                items.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }

            holder.itemView.setOnClickListener {
                showBookDialog(holder.adapterPosition)
            }
        }

        override fun getItemCount(): Int = items.size
    }
}