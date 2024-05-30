package com.example.tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminAdapter(private val usersList: ArrayList<Users>)
    : RecyclerView.Adapter<AdminAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_block_admin, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val idUser: TextView = itemView.findViewById(R.id.idUser)
        private val lastNameUser: TextView = itemView.findViewById(R.id.lastNameUser)
        private val nombreUser: TextView = itemView.findViewById(R.id.nombreUser)
        private val emailUser: TextView = itemView.findViewById(R.id.emailUser)
        private val addressUser: TextView = itemView.findViewById(R.id.addressUser)

        fun bind(user: Users) {
            idUser.text = user.Id
            lastNameUser.text = user.lastName
            nombreUser.text = user.firstName
            emailUser.text = user.email
            addressUser.text = user.address
        }
    }
}
