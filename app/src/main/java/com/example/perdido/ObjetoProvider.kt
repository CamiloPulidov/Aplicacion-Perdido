package com.example.perdido
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore



data class MyItem(
    val imageResource: Int, // Recurso de imagen local
    val imageUrl: String, // Usaremos una URL para la imagen
    val title: String,
    val descripcion: String,
    val lugar: String
)




class ObjetoProvider {

    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val collectionName = "objetosPerdidos"

        // Método para agregar un objeto a Firebase Firestore
        fun agregarObjeto(imageUrl: String, title: String, descripcion: String, lugar: String) {
            val objeto = hashMapOf(
                "imageUrl" to imageUrl,
                "title" to title,
                "descripcion" to descripcion,
                "lugar" to lugar
            )

            db.collection(collectionName)
                .add(objeto)
                .addOnSuccessListener {
                    Log.d("Firebase", "Objeto agregado correctamente")
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al agregar el objeto", e)
                }
        }


        // Método para obtener objetos desde Firebase Firestore
        fun obtenerObjetos(callback: (List<MyItem>) -> Unit) {
            db.collection(collectionName)
                .get()
                .addOnSuccessListener { result ->
                    val objetos = result.map { document ->
                        document.toObject(MyItem::class.java)
                    }
                    callback(objetos)
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error obteniendo objetos", e)
                }
        }
    }
}