import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

data class MyItem(
    val imageResource: Int = 0, // Recurso de imagen local
    val imageUrl: String = "", // URL de la imagen
    val title: String = "",
    val descripcion: String = "",
    val lugar: String = "",
    val tipo: String = "",
    val idUsuario: String = "",
    var idObjeto: String = "" // Campo id para almacenar el ID del documento
)

class ObjetoProvider {

    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val collectionName1 = "objetosPerdidos"

        // Método para agregar un objeto a Firebase Firestore y asignar su ID al campo 'id'
        fun agregarObjeto(imageUrl: String, title: String, descripcion: String, lugar: String, tipo: String, idUsuario: String) {
            val objeto = hashMapOf(
                "imageUrl" to imageUrl,
                "title" to title,
                "descripcion" to descripcion,
                "lugar" to lugar,
                "tipo" to tipo,
                "idUsuario" to idUsuario
            )

            db.collection(collectionName1)
                .add(objeto)
                .addOnSuccessListener { documentReference ->
                    val idObjeto = documentReference.id
                    documentReference.update("id", idObjeto) // Actualiza el campo 'id' en el documento
                    Log.d("Firebase", "Objeto agregado correctamente con ID: $idObjeto")
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al agregar el objeto", e)
                }
        }

        // Método para obtener objetos desde Firebase Firestore con sus IDs
        fun obtenerObjetos(callback: (List<MyItem>) -> Unit) {
            db.collection(collectionName1)
                .get()
                .addOnSuccessListener { result ->
                    val objetos = result.map { document ->
                        val item = document.toObject(MyItem::class.java)
                        item.idObjeto = document.id // Asigna el ID del documento al campo id del objeto
                        item
                    }
                    callback(objetos)
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error obteniendo objetos", e)
                }
        }
    }
}
