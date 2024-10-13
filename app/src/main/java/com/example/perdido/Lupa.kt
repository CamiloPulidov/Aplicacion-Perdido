package com.example.perdido

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Lupa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lupa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lupa)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boton: ImageButton = findViewById(R.id.imageButton)
        val boton2: ImageButton = findViewById(R.id.imageButton3)

        boton.setOnClickListener {
            val intent = Intent(this,Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        boton2.setOnClickListener {
            val intent = Intent(this,Usuario::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }
}