package com.example.perdido

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Editar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.editar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boton: Button = findViewById(R.id.button3)

        boton.setOnClickListener {
            val intent = Intent(this,Usuario::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val idUsuario = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        val editTextNombre: EditText = findViewById(R.id.editTextNombre)
        val editTextContrasena: EditText = findViewById(R.id.editTextContrasena)
        val editTextApellido: EditText = findViewById(R.id.editTextApellido)
        val editTextCorreo: EditText = findViewById(R.id.editTextCorreo)
        val editTextTelefono: EditText = findViewById(R.id.editTextTelefono)

        Registro.obtenerUsuario(idUsuario) { usuarioDetalles ->
            if (usuarioDetalles != null) {
                Log.d("Firebase", "Correo: ${usuarioDetalles.correo}, Teléfono: ${usuarioDetalles.telefono}")

                editTextNombre.setText(usuarioDetalles.nombre)
                editTextApellido.setText(usuarioDetalles.apellido)
                editTextContrasena.setText(usuarioDetalles.contrasena)
                editTextCorreo.setText(usuarioDetalles.correo)
                editTextTelefono.setText(usuarioDetalles.telefono)

            } else {
                Log.d("Firebase", "No se pudo obtener el usuario")
            }
        }

        buttonEdit.setOnClickListener {
            val isEditing = editTextNombre.isEnabled
            editTextNombre.isEnabled = !isEditing
            editTextApellido.isEnabled = !isEditing
            editTextCorreo.isEnabled= !isEditing
            editTextContrasena.isEnabled= !isEditing
            editTextTelefono.isEnabled= !isEditing

            if (!isEditing) {
                // Cambiar el texto del botón a "Guardar"
                buttonEdit.text = "Guardar"
            } else {
                // Guardar cambios en Firebase usando ObjetoProvider
                val nuevoNombre = editTextNombre.text.toString()
                val nuevoApellido = editTextApellido.text.toString()
                val nuevoCorreo= editTextCorreo.text.toString()
                val nuevaContrasena = editTextContrasena.text.toString()
                val nuevoTelefono= editTextTelefono.text.toString()

                Registro.actualizarUsuario(
                    idUsuario= idUsuario,
                    nuevoNombre = nuevoNombre,
                    nuevoApellido = nuevoApellido,
                    nuevoCorreo= nuevoCorreo,
                    nuevaContrasena= nuevaContrasena,
                    nuevoTelefono= nuevoTelefono,

                    onSuccess = {
                        Log.d("Usuarii", "Datos actualizados correctamente")
                    },
                    onFailure = { e ->
                        Log.w("Usuario", "Error al actualizar datos", e)
                    }
                )

                // Cambiar el texto del botón a "Editar"
                buttonEdit.text = "Editar"
            }
        }

    }
}