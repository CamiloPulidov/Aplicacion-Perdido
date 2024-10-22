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

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boton: Button = findViewById(R.id.button3)
        val boton2: Button = findViewById(R.id.button4)



        boton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        boton2.setOnClickListener {
            val correo = findViewById<EditText>(R.id.editTextTextPostalAddress).text.toString().trim()
            val contrasena = findViewById<EditText>(R.id.editTextTextPassword).text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Usa el callback de `iniciarSesion`
                Registro.iniciarSesion(correo, contrasena) { success ->
                    if (success) {
                        // Inicio de sesión exitoso
                        val intent = Intent(this, Casa::class.java)
                        startActivity(intent)
                    } else {
                        // Credenciales incorrectas
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }




    }
}