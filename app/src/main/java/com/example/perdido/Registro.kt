package com.example.perdido
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


data class MyUsers(
    var idUsuario: String = "",
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String,
    val telefono: String
)


class Registro {

    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val collectionName = "usuarios"

        // Método para agregar un registro de usuario a Firebase Firestore
        fun agregarRegistro(nombre: String, apellido: String, correo: String, contrasena: String, telefono: String) {
            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Usuario registrado en Firebase Authentication
                        val idUsuario = auth.currentUser?.uid ?: ""
                        val usuario = MyUsers(idUsuario, nombre, apellido, correo, contrasena, telefono)

                        // Ahora guarda el usuario en Firestore
                        db.collection(collectionName).document(idUsuario)
                            .set(usuario)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Usuario registrado con ID: $idUsuario")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firestore", "Error al guardar el usuario en Firestore", e)
                            }
                    } else {
                        Log.w("FirebaseAuth", "Error en el registro de usuario", task.exception)
                    }
                }
        }


        // Método para verificar el inicio de sesión en Firebase Firestore
        fun iniciarSesion(correo: String, contrasena: String, callback: (Boolean) -> Unit) {
            val auth = FirebaseAuth.getInstance()

            auth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true) // Sesión iniciada correctamente
                    } else {
                        Log.w("FirebaseAuth", "Error en el inicio de sesión", task.exception)
                        callback(false) // Error en el inicio de sesión
                    }
                }
        }


    }
}