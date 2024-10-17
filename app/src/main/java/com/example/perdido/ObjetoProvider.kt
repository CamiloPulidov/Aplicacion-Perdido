package com.example.perdido




data class MyItem(  val imageResource: Int, // Para imágenes de recursos
                    val title: String,
                    val descripcion: String,
                    val lugar: String
)
class ObjetoProvider {
    companion object{
        // Lista de datos
        val itemList = mutableListOf(
            MyItem(imageResource = R.drawable.lupa1, title = "OBJETO 1", descripcion = "una lupa", lugar = "bogota"),
            MyItem(imageResource = R.drawable.logo, title = "OBJETO 2", descripcion = "un logo", lugar = "bogota"),
            MyItem(imageResource = R.drawable.usuario1, title = "OBJETO 3", descripcion = "un usuario", lugar = "bogota")
        )
        // Método para agregar un objeto con un recurso de imagen
        fun agregarObjeto( title: String, descripcion: String, lugar: String) {
            itemList.add(MyItem(imageResource = R.drawable.lupa1, title = title, descripcion = descripcion, lugar = lugar))
        }


    }
}