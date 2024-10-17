package com.example.perdido

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity3)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boton: Button = findViewById(R.id.button5)
        val boton2: Button = findViewById(R.id.button6)


        val nombre = findViewById<EditText>(R.id.editTextText)

        val apellido = findViewById<EditText>(R.id.editTextText2)

        val correo = findViewById<EditText>(R.id.editTextTextEmailAddress)

        val contrasena = findViewById<EditText>(R.id.editTextTextPassword3)

        val repContrasena = findViewById<EditText>(R.id.editTextTextPassword2)

        val telefono = findViewById<EditText>(R.id.editTextPhone)


        boton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        boton2.setOnClickListener {
            val textoNombre = nombre.text.toString().trim()
            val textoApellido = apellido.text.toString().trim()
            val textoCorreo = correo.text.toString().trim()
            val textoContrasena = contrasena.text.toString().trim()
            val textoRepContrasena = repContrasena.text.toString().trim()
            val textoTelefono = telefono.text.toString().trim()
            // Validar que ningún campo esté vacío

            if (textoNombre.isEmpty() || textoApellido.isEmpty() || textoCorreo.isEmpty() ||
                textoContrasena.isEmpty() || textoRepContrasena.isEmpty() || textoTelefono.isEmpty())
            {

                // Mostrar un mensaje de error (puede ser un Toast o un mensaje en un TextView)
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
            else
            {
                // Si todos los campos están llenos, procede con la acción
                if (textoContrasena == textoRepContrasena)
                {
                    // Las contraseñas coinciden, agregar registro
                    Registro.agregarRegistro(
                        textoNombre,
                        textoApellido,
                        textoCorreo,
                        textoContrasena,
                        textoTelefono
                    )

                    // Iniciar la nueva actividad
                    val intent = Intent(this, Casa::class.java)
                    startActivity(intent)
                }
                else
                {
                    // Las contraseñas no coinciden, mostrar un mensaje de error
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}