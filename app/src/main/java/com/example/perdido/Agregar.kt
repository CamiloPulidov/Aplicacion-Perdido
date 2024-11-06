package com.example.perdido


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Agregar : AppCompatActivity() {

    private var imageUri: Uri? = null  // Variable para almacenar la URI de la imagen seleccionada

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

        val imageButton = findViewById<ImageButton>(R.id.mostrar)

        var objetoTipo: String = "perdido"

        boton.setOnClickListener {
            val intent = Intent(this, Casa::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        boton2.setOnClickListener {
            boton3.setBackgroundColor(ContextCompat.getColor(this, R.color.gris))
            boton2.setBackgroundColor(ContextCompat.getColor(this, R.color.verde1))
            objetoTipo = "perdido"
        }

        boton3.setOnClickListener {
            boton2.setBackgroundColor(ContextCompat.getColor(this, R.color.gris))
            boton3.setBackgroundColor(ContextCompat.getColor(this, R.color.verde1))
            objetoTipo = "encontrado"
        }

        boton4.setOnClickListener {
            val textoNombre = nombre.text.toString().trim()
            val textoDescripcion = descripcion.text.toString().trim()
            val textoDonde = donde.text.toString().trim()
            val textoTipo = objetoTipo.trim()

            if (textoNombre.isEmpty() || textoDescripcion.isEmpty() || textoDonde.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí deberías obtener el ID del usuario autenticado, por ejemplo:
                val idUsuario = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                // Asegúrate de tener una URL de imagen, aquí puedes usar una imagen fija o una subida a Firebase Storage
                val imageUrl = imageUri?.toString() ?: "https://www.google.com/search?sca_esv=5e5c67271c4d4da4&rlz=1C1ALOY_esCO977CO977&sxsrf=ADLYWIISLpviy5RO8n2N1P51g6NhjQNnuA:1730870556408&q=imagen+llaves&udm=2&fbs=AEQNm0CbCVgAZ5mWEJDg6aoPVcBgWizR0-0aFOH11Sb5tlNhd7Qv31WAq-g3XdD7m281OKyew6CGJrEYYQ4lESOC_x5KsvIVlG201Lyb3dylWXbacfPj5A3FfHczMG9d0EgLsThlsG8ygyJxdp_w2ISJmk02deuCKjE2H1-b7PIsmjxZ-MrEkf8KY9ZqmF-4Rxf4QHsN4LHDp1LtF9BHlqr3BH9uTtNMPw&sa=X&ved=2ahUKEwiCyaql-8aJAxWnRzABHfrJBxAQtKgLegQIFxAB&biw=1536&bih=730&dpr=1.25#vhid=Gy2Be9X-YOEuSM&vssid=mosaic" // Cambia esto por la URL correcta

                ObjetoProvider.agregarObjeto(
                    imageUrl,
                    textoNombre,
                    textoDescripcion,
                    textoDonde,
                    textoTipo,
                    idUsuario
                )

                val intent = Intent(this, Casa::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }

        // Abrir la galería cuando se haga clic en el ImageButton
        imageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100) // Código de solicitud 100
        }
    }

    // Manejar el resultado de la selección de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data // Obtén la URI de la imagen seleccionada
            imageUri?.let {
                // Coloca la imagen seleccionada en el ImageButton
                val imageButton = findViewById<ImageButton>(R.id.mostrar)
                imageButton.setImageURI(it)
                Log.d("registrando", "el uri es $imageUri")
            }
        }
    }
}
