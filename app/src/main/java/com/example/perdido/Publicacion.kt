package com.example.perdido

import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Publicacion : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance() // Conexión a Firestore
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



        // Obtener los datos del intent
        val imageResource = intent.getIntExtra("imageResource", 0)
        val title = intent.getStringExtra("title")
        val descip =intent.getStringExtra("descripcion")
        val lugar =intent.getStringExtra("lugar")
        val imageUriString = intent.getStringExtra("imageUrl")




        boton.setOnClickListener {
            val intent = Intent(this,Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val boton2: Button = findViewById(R.id.button16)
        val idUsuario = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val idObjeto = intent.getStringExtra("idObjeto") ?: ""

// Verificar el estado inicial de la relación y configurar el texto del botón
        ListaGuardado.existeRelacion(idUsuario, idObjeto) { existe ->
            boton2.text = if (existe) "Guardado" else "Guardar"
        }

// Manejar el clic en el botón para cambiar la relación en tiempo real
        boton2.setOnClickListener {
            // Ejecutar la función agregarRelacion para alternar entre agregar/eliminar
            ListaGuardado.agregarRelacion(idUsuario, idObjeto)

            // Escuchar los cambios en Firestore para actualizar el botón automáticamente
            db.collection("userObjectRelations")
                .whereEqualTo("idUsuario", idUsuario)
                .whereEqualTo("idObjeto", idObjeto)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("Firestore", "Error al escuchar los cambios en la relación", e)
                        return@addSnapshotListener
                    }

                    // Actualizar el texto del botón en función del estado actual de la relación
                    val existe = snapshot != null && !snapshot.isEmpty
                    boton2.text = if (existe) "Guardado" else "Guardar"
                }
        }




        val editTextNombre: EditText = findViewById(R.id.editTextNombre)
        val editTextDescripcion: EditText = findViewById(R.id.editTextDescripcion)
        val editTextLugar:EditText = findViewById(R.id.editTextLugar)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)

// Configurar los valores iniciales
        imageView.setImageResource(imageResource)
        editTextNombre.setText(title)
        editTextDescripcion.setText(descip)
        editTextLugar.setText(lugar)

        buttonEdit.visibility=Button.GONE

        buttonEdit.setOnClickListener {
            val isEditing = editTextNombre.isEnabled
            editTextNombre.isEnabled = !isEditing
            editTextDescripcion.isEnabled = !isEditing
            editTextLugar.isEnabled= !isEditing

            if (!isEditing) {
                // Cambiar el texto del botón a "Guardar"
                buttonEdit.text = "Guardar"
            } else {
                // Guardar cambios en Firebase usando ObjetoProvider
                val nuevoNombre = editTextNombre.text.toString()
                val nuevaDescripcion = editTextDescripcion.text.toString()
                val nuevoLugar= editTextLugar.text.toString()

                ObjetoProvider.actualizarObjeto(
                    idObjeto = idObjeto,
                    nuevoTitle = nuevoNombre,
                    nuevaDescripcion = nuevaDescripcion,
                    nuevoLugar= nuevoLugar,
                    onSuccess = {
                        Log.d("Publicacion", "Datos actualizados correctamente")
                    },
                    onFailure = { e ->
                        Log.w("Publicacion", "Error al actualizar datos", e)
                    }
                )

                // Cambiar el texto del botón a "Editar"
                buttonEdit.text = "Editar"
            }
        }

        val UsuarioActual = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val v_nombre = findViewById<TextView>(R.id.textViewNombre)
        val v_correo = findViewById<TextView>(R.id.textViewCorreo)
        val v_telefono = findViewById<TextView>(R.id.textViewNumero)

        // Obtener el ID del usuario asociado al objeto
        ObjetoProvider.obtenerIdUsuario(idObjeto) { usuarioData ->
            val idUsuario = usuarioData?.idUsuario
            if (idUsuario != null) {
                Log.d("Firebase", "ID de usuario: $idUsuario")

                if (idUsuario==UsuarioActual){
                    buttonEdit.visibility=Button.VISIBLE
                }
                // Obtener y mostrar datos del usuario
                Registro.obtenerUsuario(idUsuario) { usuarioDetalles ->
                    if (usuarioDetalles != null) {
                        Log.d("Firebase", "Correo: ${usuarioDetalles.correo}, Teléfono: ${usuarioDetalles.telefono}")
                        v_correo.text = usuarioDetalles.correo
                        v_telefono.text = usuarioDetalles.telefono
                        val nombre=usuarioDetalles.nombre
                        val apellido=usuarioDetalles.apellido
                        v_nombre.text= nombre +" " +apellido
                    } else {
                        Log.d("Firebase", "No se pudo obtener el usuario")
                    }
                }
            } else {
                Log.d("Firebase", "No se pudo obtener el ID de usuario")
            }
        }










    }

}