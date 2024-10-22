package com.example.perdido
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


data class MyUsers(
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
            val usuario = MyUsers(nombre, apellido, correo, contrasena, telefono)

            db.collection(collectionName)
                .add(usuario)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "Usuario registrado con ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al registrar el usuario", e)
                }
        }

        // Método para verificar el inicio de sesión en Firebase Firestore
        fun iniciarSesion(correo: String, contrasena: String, callback: (Boolean) -> Unit) {
            db.collection(collectionName)
                .whereEqualTo("correo", correo)
                .whereEqualTo("contrasena", contrasena)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        callback(false) // No se encontró el usuario
                    } else {
                        callback(true) // Se encontró el usuario
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error en la consulta de inicio de sesión", e)
                    callback(false)
                }
        }

    }
}