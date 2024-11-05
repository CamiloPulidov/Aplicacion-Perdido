package com.example.perdido

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.usuario)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boton: ImageButton = findViewById(R.id.imageButton)
        val boton2: ImageButton = findViewById(R.id.imageButton2)
        val boton6: Button = findViewById(R.id.button12)
        val boton5: Button = findViewById(R.id.button11)
        val boton3: Button = findViewById(R.id.button9)
        val boton4: Button = findViewById(R.id.button10)

        boton.setOnClickListener {
            val intent = Intent(this,Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton2.setOnClickListener {
            val intent = Intent(this,Lupa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton6.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }

        boton3.setOnClickListener {
            val intent = Intent(this,MisPublicaciones::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton4.setOnClickListener {
            val intent = Intent(this,Guardado::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton5.setOnClickListener {
            val intent = Intent(this,Editar::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }


    }
}