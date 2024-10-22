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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class Casa : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() // Conexión a Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.casa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.casa)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.rec)
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Inicializa el adapter vacío
        val adapter = MyAdapter(emptyList()) { item ->
            // Abrir una nueva actividad cuando se haga clic en un elemento
            val intent = Intent(this, Publicacion::class.java)
            intent.putExtra("imageResource", item.imageResource) // Pasar la imagen del objeto
            intent.putExtra("title", item.title) // Pasar el título del objeto
            intent.putExtra("descripcion", item.descripcion) // Pasar la descripción del objeto
            intent.putExtra("lugar", item.lugar) // Pasar el lugar del objeto
            startActivity(intent)
        }
        recyclerView.adapter = adapter

// Obtener los datos desde Firebase y actualizar el adapter
        db.collection("objetosPerdidos")
            .get()
            .addOnSuccessListener { result ->
                val itemList = result.map { document ->
                    MyItem(
                        imageResource = R.drawable.lupa2, // Recurso local predeterminado
                        imageUrl = document.getString("imageUrl") ?: "", // Asegúrate de que tu Firestore tenga un campo "imageUrl"
                        title = document.getString("title") ?: "",
                        descripcion = document.getString("descripcion") ?: "",
                        lugar = document.getString("lugar") ?: ""
                    )
                }
                adapter.updateData(itemList) // Actualiza los datos del RecyclerView
            }
            .addOnFailureListener { exception ->
                // Manejo de errores, en caso de fallo en la consulta
            }

        val boton: ImageButton = findViewById(R.id.imageButton2)
        val boton2: ImageButton = findViewById(R.id.imageButton3)
        val boton3: Button = findViewById(R.id.button8)
        val boton4: ImageButton = findViewById(R.id.roundButton)


        boton.setOnClickListener {
            val intent = Intent(this,Lupa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        boton2.setOnClickListener {
            val intent = Intent(this,Usuario::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        boton3.setOnClickListener {
            val intent = Intent(this,Casa2::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        boton4.setOnClickListener {
            val intent = Intent(this,Agregar::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }
}