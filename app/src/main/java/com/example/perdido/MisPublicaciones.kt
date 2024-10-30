package com.example.perdido

import MyItem
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MisPublicaciones : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() // Conexión a Firestore
    private lateinit var adapter: MyAdapter // Inicialización del adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.mispublicaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mispublicaciones)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView: RecyclerView = findViewById(R.id.rec)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializa el adapter vacío
        adapter = MyAdapter(emptyList()) { item: MyItem ->
            // Abrir una nueva actividad cuando se haga clic en un elemento
            val intent = Intent(this, Publicacion::class.java)
            intent.putExtra("imageResource", item.imageResource) // Pasar la imagen del objeto
            intent.putExtra("title", item.title) // Pasar el título del objeto
            intent.putExtra("descripcion", item.descripcion) // Pasar la descripción del objeto
            intent.putExtra("lugar", item.lugar) // Pasar el lugar del objeto
            intent.putExtra("idObjeto", item.idObjeto) // Asegúrate de pasar el idObjeto
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Aquí deberías obtener el ID del usuario autenticado, por ejemplo:
        val idUsuario = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        cargarLista(idUsuario)

        val boton: Button = findViewById(R.id.button3)

        boton.setOnClickListener {
            val intent = Intent(this,Usuario::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }
    // Función para cargar la lista filtrada según el tipo
    private fun cargarLista(id: String) {
        // Obtener los datos desde Firebase y actualizar el adapter
        db.collection("objetosPerdidos")
            .whereEqualTo("idUsuario", id) // Filtra por el tipo (perdido o encontrado)
            .get()
            .addOnSuccessListener { result ->
                val itemList = result.map { document ->
                    MyItem(
                        imageResource = R.drawable.lupa2, // Recurso local predeterminado
                        imageUrl = document.getString("imageUrl") ?: "", // Asegúrate de que tu Firestore tenga un campo "imageUrl"
                        title = document.getString("title") ?: "",
                        descripcion = document.getString("descripcion") ?: "",
                        lugar = document.getString("lugar") ?: "",
                        tipo = document.getString("tipo") ?: "",
                        idObjeto = document.id // Asigna el ID del documento aquí

                    )
                }
                adapter.updateData(itemList) // Actualiza los datos del RecyclerView
            }
            .addOnFailureListener { exception ->
                // Manejo de errores, en caso de fallo en la consulta
            }
    }
}