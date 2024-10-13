package com.example.perdido

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val itemList: List<MyItem>, private val onItemClick: (MyItem) -> Unit) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // ViewHolder: Contiene las vistas para cada elemento
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView4)
        val title: TextView = itemView.findViewById(R.id.textView5)
    }

    // Inflar el layout para cada elemento de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    // Enlazar los datos a las vistas del ViewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.title.text = currentItem.title

        // Asignar el listener de clic
        holder.itemView.setOnClickListener {
            onItemClick(currentItem)  // Llamamos al listener pasando el item clickeado
        }
    }

    // Retornar el tamaño de la lista
    override fun getItemCount() = itemList.size
}


