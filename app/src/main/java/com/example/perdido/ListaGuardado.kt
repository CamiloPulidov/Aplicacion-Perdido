package com.example.perdido

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

data class Guardados(
    val idUsuario: String = "",
    val idObjeto: String = ""
)

class ListaGuardado {
    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val collectionName = "userObjectRelations" // Nombre de la colección en Firestore

        // Función para verificar, eliminar o agregar una relación entre usuario y objeto
        fun agregarRelacion(idUsuario: String, idObjeto: String) {
            db.collection(collectionName)
                .whereEqualTo("idUsuario", idUsuario)
                .whereEqualTo("idObjeto", idObjeto)
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        // Si no existe, agrega la relación
                        val relacion = Guardados(idUsuario = idUsuario, idObjeto = idObjeto)
                        db.collection(collectionName)
                            .add(relacion)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Relación agregada correctamente")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firebase", "Error al agregar la relación", e)
                            }
                    } else {
                        // Si ya existe, elimínala
                        eliminarRelacionExistente(result)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al verificar la relación existente", e)
                }
        }

        // Función para eliminar la relación existente
        private fun eliminarRelacionExistente(result: QuerySnapshot) {
            for (document in result) {
                db.collection(collectionName).document(document.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("Firebase", "Relación eliminada correctamente")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firebase", "Error al eliminar la relación", e)
                    }
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
