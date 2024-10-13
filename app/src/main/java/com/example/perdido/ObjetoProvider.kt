package com.example.perdido


data class MyItem( val imageResource: Int,
                   val title: String,
                   val descripcion: String,
                   val lugar: String
)
class ObjetoProvider {
    companion object{
        // Lista de datos
        val itemList = mutableListOf(
            MyItem(R.drawable.lupa1, "OBJETO 1","una lupa","bogota"),
            MyItem(R.drawable.logo, "OBJETO 2","un logo","bogota"),
            MyItem(R.drawable.usuario1, "OBJETO 3","un usuario","bogota")
        )
    }
}