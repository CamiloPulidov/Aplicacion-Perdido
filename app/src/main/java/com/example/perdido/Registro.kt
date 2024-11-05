package com.example.perdido

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
                        val idUsuario = auth.currentUser?.uid ?: ""
                        val usuario = MyUsers(idUsuario, nombre, apellido, correo, contrasena, telefono)

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
                        callback(true)
                    } else {
                        Log.w("FirebaseAuth", "Error en el inicio de sesión", task.exception)
                        callback(false)
                    }
                }
        }

        // Método para obtener solo el correo y el teléfono de un usuario desde Firestore
        fun obtenerUsuario(idUsuario: String, callback: (MyUsers?) -> Unit) {
            db.collection(collectionName).document(idUsuario)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val correo = document.getString("correo") ?: ""
                        val telefono = document.getString("telefono") ?: ""
                        val apellido = document.getString("apellido") ?: ""
                        val nombre = document.getString("nombre") ?: ""
                        val contrasena = document.getString("contrasena") ?: ""
                        val usuarioDetalles = MyUsers(idUsuario, nombre, apellido, correo, contrasena, telefono)
                        callback(usuarioDetalles)
                    } else {
                        Log.d("Firestore", "Usuario no encontrado con ID: $idUsuario")
                        callback(null)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al obtener el usuario", e)
                    callback(null)
                }
        }

        // Método para actualizar los campos en Firebase y Firebase Authentication
        fun actualizarUsuario(
            idUsuario: String, nuevoNombre: String, nuevoApellido: String,
            nuevoCorreo: String, nuevaContrasena: String, nuevoTelefono: String,
            onSuccess: () -> Unit, onFailure: (Exception) -> Unit
        ) {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            if (currentUser != null && currentUser.uid == idUsuario) {
                // Primero, actualizar correo y contraseña en Firebase Authentication
                currentUser.updateEmail(nuevoCorreo)
                    .addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            currentUser.updatePassword(nuevaContrasena)
                                .addOnCompleteListener { passwordTask ->
                                    if (passwordTask.isSuccessful) {
                                        // Actualizar la información en Firestore
                                        actualizarDatosEnFirestore(idUsuario, nuevoNombre, nuevoApellido, nuevoCorreo, nuevaContrasena, nuevoTelefono, onSuccess, onFailure)
                                    } else {
                                        Log.w("FirebaseAuth", "Error al actualizar la contraseña", passwordTask.exception)
                                        onFailure(passwordTask.exception ?: Exception("Error desconocido al actualizar la contraseña"))
                                    }
                                }
                        } else {
                            Log.w("FirebaseAuth", "Error al actualizar el correo", emailTask.exception)
                            onFailure(emailTask.exception ?: Exception("Error desconocido al actualizar el correo"))
                        }
                    }
            } else {
                onFailure(Exception("Usuario no autenticado o ID de usuario no coincide"))
            }
        }

        // Método auxiliar para actualizar los datos en Firestore
        private fun actualizarDatosEnFirestore(
            idUsuario: String, nuevoNombre: String, nuevoApellido: String,
            nuevoCorreo: String, nuevaContrasena: String, nuevoTelefono: String,
            onSuccess: () -> Unit, onFailure: (Exception) -> Unit
        ) {
            val updates = hashMapOf(
                "nombre" to nuevoNombre,
                "apellido" to nuevoApellido,
                "correo" to nuevoCorreo,
                "contrasena" to nuevaContrasena,
                "telefono" to nuevoTelefono,
            )

            db.collection(collectionName)
                .document(idUsuario)
                .update(updates as Map<String, Any>)
                .addOnSuccessListener {
                    Log.d("Firebase", "Usuario actualizado correctamente con ID: $idUsuario")
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al actualizar el usuario en Firestore", e)
                    onFailure(e)
                }
        }
    }
}
