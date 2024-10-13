package com.example.perdido


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Agregar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.agregar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.agregar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boton: Button = findViewById(R.id.button3)
        val boton2: Button = findViewById(R.id.button13)
        val boton3: Button = findViewById(R.id.button14)
        val boton4: Button = findViewById(R.id.button15)

        boton.setOnClickListener {
            val intent = Intent(this,Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton2.setOnClickListener {
            boton3.setBackgroundColor(ContextCompat.getColor(this, R.color.gris))
            boton2.setBackgroundColor(ContextCompat.getColor(this, R.color.verde1))
        }

        boton3.setOnClickListener {
            boton2.setBackgroundColor(ContextCompat.getColor(this, R.color.gris))
            boton3.setBackgroundColor(ContextCompat.getColor(this, R.color.verde1))
        }

        boton4.setOnClickListener {

            val intent = Intent(this,Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }
}