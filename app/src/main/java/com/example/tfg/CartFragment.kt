package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class CartFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtEmpty: TextView
    private lateinit var productsArrayList: ArrayList<Products>
    private lateinit var myAdapter: CartAdapter
    private lateinit var db:FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
        val mainActivity = requireActivity() as MainActivity
        val actionBarDrawerToggle = mainActivity.getDrawerToggle()
        actionBarDrawerToggle?.setDrawerIndicatorEnabled(false)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        recyclerView = view.findViewById<RecyclerView>(R.id.ListaProductos)
        txtEmpty = view.findViewById<TextView>(R.id.txtEmpty)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        productsArrayList = arrayListOf()
        myAdapter = CartAdapter(productsArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        val imgbtnSalir = view?.findViewById<ImageView>(R.id.imgbtnSalir)
        imgbtnSalir?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, LoginFragment()).commit()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val removedProduct = productsArrayList.removeAt(position)
                myAdapter.notifyItemRemoved(position)
                DeleteItem(removedProduct.Name)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)


    }
    private fun DeleteItem(productName: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.email.toString()
        val collectionRef = db.collection("Users").document(userId).collection("Cart$userId")
        collectionRef.whereEqualTo("Name", productName).get()
            .addOnSuccessListener { querySnapshot ->
                for (documentSnapshot in querySnapshot.documents) {
                    val productId = documentSnapshot.id
                    Log.d("Firestore", "ID del producto '$productName': $productId")
                    val documentRef = collectionRef.document(productId)
                    documentRef.delete()
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Elemento eliminado: $productName", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore Error", "Error deleting document: ${e.message}")
                        }
                }
            }
    }
    private fun EventChangeListener() {
        val userId = FirebaseAuth.getInstance().currentUser?.email
        val cartCollectionRef = db.collection("Users").document(userId.toString()).collection("Cart"+userId.toString())

        cartCollectionRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot = task.result
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    recyclerView.visibility = View.VISIBLE
                    txtEmpty.visibility = View.GONE
                    cartCollectionRef.addSnapshotListener { value, error ->
                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                            return@addSnapshotListener
                        }
                        for (dc in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                val product = dc.document.toObject(Products::class.java)
                                Log.d("Firestore Data", "${product.Name}")
                                productsArrayList.add(product)
                            }
                        }
                        myAdapter.notifyDataSetChanged()
                    }
                } else {
                    recyclerView.visibility = View.GONE
                    txtEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

}