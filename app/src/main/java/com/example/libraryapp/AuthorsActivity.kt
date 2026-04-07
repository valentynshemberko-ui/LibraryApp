package com.example.libraryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AuthorsActivity : AppCompatActivity() {

    data class Author(val name: String, val info: String)

    private val authorsList = listOf(
        Author("Джордж Оруелл", "Англійський письменник, відомий своїми антиутопіями '1984' та 'Колгосп тварин'."),
        Author("Дж.Р.Р. Толкін", "Професор Оксфордського університету, творець епічного фентезі 'Володар перснів'."),
        Author("Тарас Шевченко", "Український поет, письменник, художник. Центральна фігура українського національного відродження."),
        Author("Джейн Остін", "Англійська письменниця, чиї твори є визнаними шедеврами класичної світової літератури.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authors)

        val rvAuthors = findViewById<RecyclerView>(R.id.rvAuthors)
        rvAuthors.layoutManager = LinearLayoutManager(this)
        rvAuthors.adapter = AuthorAdapter(authorsList)
    }

    inner class AuthorAdapter(private val items: List<Author>) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {

        inner class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvAuthorName: TextView = itemView.findViewById(R.id.tvAuthorName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_author, parent, false)
            return AuthorViewHolder(view)
        }

        override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
            val author = items[position]
            holder.tvAuthorName.text = author.name

            holder.itemView.setOnClickListener {
                AlertDialog.Builder(this@AuthorsActivity)
                    .setTitle(author.name)
                    .setMessage(author.info)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        override fun getItemCount(): Int = items.size
    }
}