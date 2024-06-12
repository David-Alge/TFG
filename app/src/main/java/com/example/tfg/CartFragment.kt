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
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
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
    private lateinit var db: FirebaseFirestore
    private lateinit var btnEmpty: Button
    private lateinit var btnBuy: Button
    private lateinit var txtTotal: TextView

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
        recyclerView = view.findViewById(R.id.ListaProductos)
        txtEmpty = view.findViewById(R.id.txtEmpty)
        txtTotal = view.findViewById(R.id.txtTotal)
        btnEmpty = view.findViewById(R.id.btnEmptyCart)
        btnBuy = view.findViewById(R.id.btnBuy)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        productsArrayList = arrayListOf()
        myAdapter = CartAdapter(productsArrayList) { totalPrice ->
            txtTotal.text = "Total: ${totalPrice}€"
        }
        recyclerView.adapter = myAdapter
        EventChangeListener()

        val imgbtnSalir = view.findViewById<ImageView>(R.id.imgbtnSalir)
        imgbtnSalir?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, LoginFragment()).commit()
        }

        btnEmpty.setOnClickListener {
            deleteAllItems()
            Toast.makeText(requireContext(),
                "All elements have been eliminated", Toast.LENGTH_SHORT
            ).show()
            txtTotal.text = "Total: 0€"
        }

        btnBuy.setOnClickListener {
            deleteAllItems()
            Toast.makeText(requireContext(),
                "Enjoy your buy", Toast.LENGTH_SHORT
            ).show()
            txtTotal.text = "Total: 0€"
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

    private fun deleteAllItems() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val collectionRef = db.collection("Users").document(userId).collection("Cart$userId")

        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (documentSnapshot in querySnapshot.documents) {
                    val productId = documentSnapshot.id
                    val documentRef = collectionRef.document(productId)
                    documentRef.delete()
                        .addOnSuccessListener {
                            productsArrayList.clear()
                            myAdapter.notifyDataSetChanged()
                            txtTotal.text = "Total: 0€"
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore Error", "Error deleting document: ${e.message}")
                        }
                }
                requireView().invalidate()
            }
    }

    private fun DeleteItem(productName: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val collectionRef = db.collection("Users").document(userId).collection("Cart$userId")
        collectionRef.whereEqualTo("Name", productName).get()
            .addOnSuccessListener { querySnapshot ->
                for (documentSnapshot in querySnapshot.documents) {
                    val productId = documentSnapshot.id
                    Log.d("Firestore", "ID del producto '$productName': $productId")
                    val documentRef = collectionRef.document(productId)
                    documentRef.delete()
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Element eliminated: $productName", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore Error", "Error deleting document: ${e.message}")
                        }
                }
            }
    }

    private fun sumarPrecios(): Int {
        var suma = 0
        for (product in productsArrayList) {
            val precioString = product.Price
            val precioNumerico = precioString.substring(0, precioString.length - 1)
            suma += precioNumerico.toInt()
        }
        Log.d("Firestore", "suma del carrito '$suma'")
        return suma
    }

    private fun EventChangeListener() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val cartCollectionRef = db.collection("Users").document(userId.toString()).collection("Cart" + userId.toString())

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
                        txtTotal.text = "Total: " + sumarPrecios().toString() + "€"
                    }
                } else {
                    recyclerView.visibility = View.GONE
                    txtEmpty.visibility = View.VISIBLE
                }
            }
        }
    }
}
