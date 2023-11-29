package com.example.tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso

class CartAdapter(private val productsList : ArrayList<Products>) : RecyclerView.Adapter<CartAdapter.MyViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_block_cart,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val products : Products = productsList[position]
        holder.Categoria.text = products.Category
        holder.Nombre.text = products.Name
        holder.Precio.text = products.Price
        holder.Charact1.text = products.Charact1
        holder.Charact2.text = products.Charact2
        holder.Charact3.text = products.Charact3

        if (products.Img.isNotEmpty()) {
            Picasso.get().load(products.Img).into(holder.Imagen)
        } else {
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
        val Charact1: TextView = itemView.findViewById(R.id.Charact1)
        val Charact2: TextView = itemView.findViewById(R.id.Charact2)
        val Charact3: TextView = itemView.findViewById(R.id.Charact3)
    }

}