package com.example.tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminProductsAdapter(
    private val productsList: ArrayList<Products>, val onClick: (Products) -> Unit
) : RecyclerView.Adapter<AdminProductsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminProductsAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_block_admin_product, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(products: Products) {

            val cardView: CardView = itemView.findViewById(R.id.product_Admin_Card)
            val idProduct: TextView = itemView.findViewById(R.id.idProduct)
            val Nombre: TextView = itemView.findViewById(R.id.Name_Admin)
            val Charact1: TextView = itemView.findViewById(R.id.Charact1_Admin)
            val Charact2: TextView = itemView.findViewById(R.id.Charact2_Admin)
            val Charact3: TextView = itemView.findViewById(R.id.Charact3_Admin)
            val Precio: TextView = itemView.findViewById(R.id.Price_Admin)

            idProduct.text = products.Id
            Nombre.text = products.Name
            Charact1.text = products.Charact1
            Charact2.text = products.Charact2
            Charact3.text = products.Charact3
            Precio.text = products.Price

            val deletebtn: Button = itemView.findViewById(R.id.btndeleteProduct)

            deletebtn.setOnClickListener {
                val prodRef =
                    FirebaseFirestore.getInstance().collection("Products").document(products.Id)
                prodRef.delete().addOnSuccessListener {
                    Toast.makeText(
                        deletebtn.context,
                        "Product ${products.Name} deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    productsList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        deletebtn.context,
                        "Error deleting product: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            cardView.setOnClickListener {
                onClick(products)
            }
        }
    }

}