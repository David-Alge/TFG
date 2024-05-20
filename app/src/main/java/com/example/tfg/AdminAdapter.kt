package com.example.tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminAdapter(private val usersList : ArrayList<Users>) : RecyclerView.Adapter<AdminAdapter.MyViewHolder>()   {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_block_admin,parent,false)
        return AdminAdapter.MyViewHolder(itemView)
    }



    override fun getItemCount(): Int {
        return usersList.size

    }

    override fun onBindViewHolder(holder: AdminAdapter.MyViewHolder, position: Int) {
        val users : Users = usersList[position]
        holder.idUser.text = users.Id
        holder.lastNameUser.text = users.address
        holder.nombreUser.text = users.email
        holder.emailUser.text = users.firstName
        holder.addressUser.text = users.lastName

    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val idUser : TextView = itemView.findViewById(R.id.idUser)
        val lastNameUser : TextView = itemView.findViewById(R.id.lastNameUser)
        val nombreUser : TextView = itemView.findViewById(R.id.nombreUser)
        val emailUser : TextView = itemView.findViewById(R.id.emailUser)
        val addressUser: TextView = itemView.findViewById(R.id.addressUser)
    }

}
