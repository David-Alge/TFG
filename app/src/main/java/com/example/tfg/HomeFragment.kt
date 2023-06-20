package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.appcompat.widget.Toolbar

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tfg.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsArrayList: ArrayList<Products>
    private lateinit var myAdapter: HomeAdapter
    private lateinit var db:FirebaseFirestore
    private lateinit var firebaseAuth:FirebaseAuth
    private var currentFilter : String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.VISIBLE
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.VISIBLE
        val mainActivity = requireActivity() as MainActivity
        val actionBarDrawerToggle = mainActivity.getDrawerToggle()
        actionBarDrawerToggle?.setDrawerIndicatorEnabled(true)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        recyclerView = binding.ListaProductos
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        val mainActivity = requireActivity() as MainActivity

        val documentId = firebaseAuth.currentUser?.uid
        val collection = db.collection("Users")
        val documento = collection.document(documentId.toString())

        documento.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val valorFirst = snapshot.getString("firstName")
                    val valorEmail = snapshot.getString("email")
                    mainActivity.changeUData("${valorFirst}", "${valorEmail}")
                }
            }



        productsArrayList = arrayListOf()
        myAdapter = HomeAdapter(productsArrayList)

        recyclerView.adapter = myAdapter

        applyFilter(currentFilter)

        val imgbtnSalir = view?.findViewById<ImageView>(R.id.imgbtnSalir)
        imgbtnSalir?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, InicioFragment()).commit()
        }
    }


    fun applyFilter(currentFilter: String) {
        productsArrayList.clear()
        Log.d("Home Fragment", "$currentFilter")
        db.collection("Products")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Home Fragment", error.message.toString())
                    return@addSnapshotListener
                }

                for (dc in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val product = dc.document.toObject(Products::class.java)
                            if (currentFilter == ""){
                                productsArrayList.add(product)
                            }else{
                                if (product.Category == currentFilter){
                                    productsArrayList.add(product)
                                }
                            }
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
    }
}