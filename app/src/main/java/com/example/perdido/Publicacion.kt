package com.example.perdido

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Publicacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.publicacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.publicacion)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView: ImageView = findViewById(R.id.imageViewDetail)
        val textView: TextView = findViewById(R.id.textViewDetail)
        val descripcion: TextView = findViewById(R.id.textView4)

        // Obtener los datos del intent
        val imageResource = intent.getIntExtra("imageResource", 0)
        val title = intent.getStringExtra("title")
        val descip =intent.getStringExtra("descripcion")
        // Establecer los datos en la vista
        imageView.setImageResource(imageResource)
        textView.text = title
        descripcion.text=descip

    }
}