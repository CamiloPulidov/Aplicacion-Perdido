package com.example.perdido

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

data class Guardados(
    val idUsuario: String = "",
    val idObjeto: String = ""
)

class ListaGuardado {
    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val collectionName = "userObjectRelations" // Nombre de la colección en Firestore

        // Función para agregar una relación entre usuario y objeto
        fun agregarRelacion(idUsuario: String, idObjeto: String) {
            val relacion = Guardados(idUsuario = idUsuario, idObjeto = idObjeto)

            db.collection(collectionName)
                .add(relacion)
                .addOnSuccessListener {
                    Log.d("Firebase", "Relación agregada correctamente")
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al agregar la relación", e)
                }
        }

        // Función para obtener todos los idObjeto de un idUsuario dado
        fun objetosPorUsuario(idUsuario: String, callback: (List<String>) -> Unit) {
            db.collection(collectionName)
                .whereEqualTo("idUsuario", idUsuario) // Filtrar por idUsuario
                .get()
                .addOnSuccessListener { result ->
                    val listaObjetos = result.map { document ->
                        document.getString("idObjeto") ?: ""
                    }.filter { it.isNotEmpty() } // Filtrar posibles valores nulos o vacíos
                    callback(listaObjetos)
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al obtener los objetos guardados", e)
                    callback(emptyList()) // Retornar una lista vacía en caso de error
                }
        }
    }
}
