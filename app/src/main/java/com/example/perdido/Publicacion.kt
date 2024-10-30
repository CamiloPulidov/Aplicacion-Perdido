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
import com.google.firebase.auth.FirebaseAuth

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

        val boton: Button = findViewById(R.id.button3)


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

        boton.setOnClickListener {
            val intent = Intent(this,Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val boton2: Button = findViewById(R.id.button16)

        boton2.setOnClickListener {
            val idUsuario = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val idObjeto = intent.getStringExtra("idObjeto") ?: ""
            ListaGuardado.agregarRelacion(idUsuario, idObjeto)
        }
    }
}