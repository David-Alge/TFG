package com.example.tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter(private val productsList : ArrayList<Products>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_block,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductAdapter.MyViewHolder, position: Int) {
        val products : Products = productsList[position]
        holder.Categoria.text = products.Category
        holder.Nombre.text = products.Name
        holder.Precio.text = products.Price

        if (products.Img.isNotEmpty()) {
            Picasso.get().load(products.Img).into(holder.Imagen)
        } else {
            // Manejar el caso de una ruta de imagen vacía o inválida
            // Por ejemplo, cargar una imagen de marcador de posición
            holder.Imagen.setImageResource(R.drawable.ic_android_black_24dp)
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val Nombre : TextView = itemView.findViewById(R.id.Nombre)
        val Precio : TextView = itemView.findViewById(R.id.Precio)
        val Imagen : ImageView = itemView.findViewById(R.id.imagen)
        val Categoria : TextView = itemView.findViewById(R.id.Categoria)

    }

}