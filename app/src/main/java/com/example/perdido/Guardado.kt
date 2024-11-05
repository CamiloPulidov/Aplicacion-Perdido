package com.example.perdido

import MyItem
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Guardado : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() // Conexión a Firestore
    private lateinit var adapter: MyAdapter // Inicialización del adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.guardado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guardado)) { v, insets ->
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

        // Obtener el ID del usuario autenticado
        val idUsuario = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Cargar los idObjeto y luego cargar la lista
        ListaGuardado.objetosPorUsuario(idUsuario) { idObjetos ->
            // Llama a cargarLista con los idObjeto obtenidos
            cargarLista(idObjetos)
        }

        val boton: Button = findViewById(R.id.button3)

        boton.setOnClickListener {
                                   val intent = Intent(this, Usuario::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    // Función para cargar la lista de objetos a partir de una lista de idObjeto
    private fun cargarLista(idObjetos: List<String>) {
        // Crear una lista mutable para almacenar los MyItem
        val itemList = mutableListOf<MyItem>()

        // Cargar cada objeto correspondiente a los idObjeto
        for (idObjeto in idObjetos) {
            db.collection("objetosPerdidos")
                .document(idObjeto) // Obtener el documento específico
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Crear el objeto MyItem a partir del documento
                        val item = MyItem(
                            imageResource = R.drawable.lupa2, // Recurso local predeterminado
                            imageUrl = document.getString("imageUrl") ?: "",
                            title = document.getString("title") ?: "",
                            descripcion = document.getString("descripcion") ?: "",
                            lugar = document.getString("lugar") ?: "",
                            tipo = document.getString("tipo") ?: "",
                            idObjeto = idObjeto // Asegúrate de incluir el ID
                        )
                        itemList.add(item) // Agregar a la lista

                        // Actualizar el adapter solo si es el último documento
                        if (itemList.size == idObjetos.size) {
                            adapter.updateData(itemList) // Actualiza los datos del RecyclerView
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Manejo de errores, en caso de fallo en la consulta
                }
        }
    }
}
