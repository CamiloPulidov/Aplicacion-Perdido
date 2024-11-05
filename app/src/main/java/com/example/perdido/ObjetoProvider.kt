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
        fun obtenerIdUsuario(idObjeto: String, callback: (String?) -> Unit) {
            db.collection(collectionName1)
                .document(idObjeto)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val idUsuario = document.getString("idUsuario")
                        callback(idUsuario) // Llama al callback con el idUsuario
                    } else {
                        Log.d("Firebase", "No se encontró el objeto con el ID especificado")
                        callback(null)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al obtener el idUsuario", e)
                    callback(null)
                }
        }

        // Método para actualizar los campos 'title' y 'descripcion' de un objeto en Firebase
        fun actualizarObjeto(idObjeto: String, nuevoTitle: String, nuevaDescripcion: String, nuevoLugar: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
            val updates = hashMapOf(
                "title" to nuevoTitle,
                "descripcion" to nuevaDescripcion,
                "lugar" to nuevoLugar
            )

            db.collection(collectionName1)
                .document(idObjeto)
                .update(updates as Map<String, Any>)
                .addOnSuccessListener {
                    Log.d("Firebase", "Objeto actualizado correctamente con ID: $idObjeto")
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error al actualizar el objeto", e)
                    onFailure(e)
                }
        }

    }
}
