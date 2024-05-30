package com.example.tfg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeAdapter(private val productsList: ArrayList<Products>, val onClick: (Products) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_block, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: HomeAdapter.MyViewHolder, position: Int) {

        holder.bind(productsList[position])


    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Products) { 
            val cardView: CardView = itemView.findViewById(R.id.productCard)
            val Nombre: TextView = itemView.findViewById(R.id.Nombre)
            val Precio: TextView = itemView.findViewById(R.id.Precio)
            val Imagen: ImageView = itemView.findViewById(R.id.imagen)
            val Categoria: TextView = itemView.findViewById(R.id.Categoria)
            val Charact1: TextView = itemView.findViewById(R.id.Charact1)
            val Charact2: TextView = itemView.findViewById(R.id.Charact2)
            val Charact3: TextView = itemView.findViewById(R.id.Charact3)

            val btnAdd: Button = itemView.findViewById(R.id.btnAdd)
            val db = FirebaseFirestore.getInstance()
            val user = FirebaseAuth.getInstance()

            val userId = user.currentUser?.email
            val cartCollectionRef = db.collection("Users").document(userId.toString())
                .collection("Cart" + userId.toString())
            val productID = productsList[adapterPosition].Id
            val productRef =
                FirebaseFirestore.getInstance().collection("Products").document(productID)

            val products: Products = productsList[position]
            Categoria.text = products.Category
            Charact1.text = products.Charact1
            Charact2.text = products.Charact2
            Charact3.text = products.Charact3
            Nombre.text = products.Name
            Precio.text = products.Price


            if (products.Img.isNotEmpty()) {
                Picasso.get().load(products.Img).into(Imagen)
            } else {
                Imagen.setImageResource(R.drawable.ic_android_black_24dp)
            }
            btnAdd.setOnClickListener {
                productRef.get().addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("Carrito", "$userId")
                        val data = document.data
                        Toast.makeText(btnAdd.context, "Product added to cart", Toast.LENGTH_SHORT)
                            .show();

                        if (data != null) {
                            cartCollectionRef.add(data)
                        }
                    }
                }
            }
            cardView.setOnClickListener {
                onClick(item)
            }
        }


    }

}