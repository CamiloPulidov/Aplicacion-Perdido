package com.example.perdido


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast

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

        val nombre = findViewById<EditText>(R.id.editTextText4)

        val descripcion = findViewById<EditText>(R.id.editTextText5)

        val donde = findViewById<EditText>(R.id.editTextText7)

        val imageView = ContextCompat.getDrawable(this, R.drawable.lupa2)

        var objetoTipo:String= "perdido"


        boton.setOnClickListener {
            val intent = Intent(this,Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton2.setOnClickListener {
            boton3.setBackgroundColor(ContextCompat.getColor(this, R.color.gris))
            boton2.setBackgroundColor(ContextCompat.getColor(this, R.color.verde1))
            objetoTipo="perdido"
        }

        boton3.setOnClickListener {
            boton2.setBackgroundColor(ContextCompat.getColor(this, R.color.gris))
            boton3.setBackgroundColor(ContextCompat.getColor(this, R.color.verde1))
            objetoTipo="encontrado"
        }

        boton4.setOnClickListener {
            val textoNombre = nombre.text.toString().trim()
            val textoDescripcion = descripcion.text.toString().trim()
            val textoDonde = donde.text.toString().trim()
            val textoTipo =objetoTipo.trim()

            if (textoNombre.isEmpty() || textoDescripcion.isEmpty() || textoDonde.isEmpty())
            {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Asegúrate de tener una URL de imagen, aquí puedes usar una imagen fija o una subida a Firebase Storage
                val imageUrl = "https://ejemplo.com/imagen.png" // Cambia esto por la URL correcta

                ObjetoProvider.agregarObjeto(
                    imageUrl,
                    textoNombre,
                    textoDescripcion,
                    textoDonde,
                    textoTipo
                )

                val intent = Intent(this, Casa::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }



    }
}