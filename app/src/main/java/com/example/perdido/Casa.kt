package com.example.perdido

import MyItem
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
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
    private lateinit var adapter: MyAdapter // Inicialización del adapter

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

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, 0, 0, 0) // Sin espacio entre elementos
            }
        })


        // Inicializa el adapter vacío
        adapter = MyAdapter(emptyList()) { item: MyItem ->
            // Abrir una nueva actividad cuando se haga clic en un elemento
            val intent = Intent(this, Publicacion::class.java)
            intent.putExtra("idObjeto", item.idObjeto) // Pasa el id del objeto
            intent.putExtra("imageResource", item.imageResource) // Pasar la imagen del objeto
            intent.putExtra("title", item.title) // Pasar el título del objeto
            intent.putExtra("descripcion", item.descripcion) // Pasar la descripción del objeto
            intent.putExtra("lugar", item.lugar) // Pasar el lugar del objeto
            intent.putExtra("idObjeto", item.idObjeto) // Asegúrate de pasar el idObjeto
            intent.putExtra("imageUrl", item.imageUrl) // Asegúrate de pasar el idObjeto
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Botones para filtrar
        val boton3: Button = findViewById(R.id.button8)
        val boton5: Button = findViewById(R.id.button7)

        val view1 = findViewById<View>(R.id.view5)
        val view2 = findViewById<View>(R.id.view6)
        view1.visibility = View.VISIBLE
        view2.visibility = View.GONE

        // Cargar lista por defecto
        cargarLista("perdido")

        boton3.setOnClickListener {
            cargarLista("encontrado") // Cambiar filtro a "encontrado"
            view1.visibility = View.GONE
            view2.visibility = View.VISIBLE
        }

        boton5.setOnClickListener {
            cargarLista("perdido") // Cambiar filtro a "perdido"
            view1.visibility = View.VISIBLE
            view2.visibility = View.GONE
        }

        val boton: ImageButton = findViewById(R.id.imageButton2)
        val boton2: ImageButton = findViewById(R.id.imageButton3)
        val boton4: ImageButton = findViewById(R.id.roundButton)

        boton.setOnClickListener {
            val intent = Intent(this, Lupa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        boton2.setOnClickListener {
            val intent = Intent(this, Usuario::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton4.setOnClickListener {
            val intent = Intent(this, Agregar::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    // Función para cargar la lista filtrada según el tipo
    private fun cargarLista(tipoMostrar: String) {
        // Obtener los datos desde Firebase y actualizar el adapter
        db.collection("objetosPerdidos")
            .whereEqualTo("tipo", tipoMostrar) // Filtra por el tipo (perdido o encontrado)
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
