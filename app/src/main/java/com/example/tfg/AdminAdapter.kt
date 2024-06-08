package com.example.tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminAdapter(private val usersList: ArrayList<Users>, val onClick: (Users) -> Unit)
    : RecyclerView.Adapter<AdminAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_block_admin, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Users) {
            val cardView: CardView = itemView.findViewById(R.id.userCard)
            val idUser: TextView = itemView.findViewById(R.id.idUser)
            val lastNameUser: TextView = itemView.findViewById(R.id.lastNameUser)
            val nombreUser: TextView = itemView.findViewById(R.id.nombreUser)
            val emailUser: TextView = itemView.findViewById(R.id.emailUser)
            val addressUser: TextView = itemView.findViewById(R.id.addressUser)
            val deletebtn: Button = itemView.findViewById(R.id.btndeleteUser)



            idUser.text = user.Id
            lastNameUser.text = user.lastName
            nombreUser.text = user.firstName
            emailUser.text = user.email
            addressUser.text = user.address

            deletebtn.setOnClickListener {
                val userRef = FirebaseFirestore.getInstance().collection("Users").document(user.Id)
                userRef.delete().addOnSuccessListener {
                    Toast.makeText(
                        deletebtn.context,
                        "User ${user.email} deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    usersList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        deletebtn.context,
                        "Error deleting user: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            cardView.setOnClickListener{
                onClick(user)
            }
        }
    }
}
