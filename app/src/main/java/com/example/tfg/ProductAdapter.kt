package com.example.tfg

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentReference

class ProductAdapter(private val productsList : ArrayList<Products>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_block,parent,false)

        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ProductAdapter.MyViewHolder, position: Int) {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance()

        val userId =user.currentUser?.uid

        val cartCollectionRef = db.collection("Users").document(userId.toString()).collection("Cart"+userId.toString())
        val productID = productsList[holder.adapterPosition].Id
        val productRef = FirebaseFirestore.getInstance().collection("Products").document(productID)

        val products : Products = productsList[position]
        holder.Categoria.text = products.Category
        holder.Nombre.text = products.Name
        holder.Precio.text = products.Price

        if (products.Img.isNotEmpty()) {
            Picasso.get().load(products.Img).into(holder.Imagen)
        } else {
            holder.Imagen.setImageResource(R.drawable.ic_android_black_24dp)
        }

        holder.btnAdd.setOnClickListener{
            productRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    println("producto: $data")
                    if (data != null) {
                        cartCollectionRef.add(data)
                    }
                }
            }
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
        val btnAdd : Button = itemView.findViewById(R.id.btnAdd)

    }

}