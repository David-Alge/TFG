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
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsArrayList: ArrayList<Products>
    private lateinit var myAdapter: ProductAdapter
    private lateinit var db:FirebaseFirestore


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
        recyclerView = binding.ListaProductos
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)




        productsArrayList = arrayListOf()
        myAdapter = ProductAdapter(productsArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        val imgbtnSalir = view?.findViewById<ImageView>(R.id.imgbtnSalir)
        imgbtnSalir?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, InicioFragment()).commit()
        }
    }

    private fun EventChangeListener() {

        db.collection("Products").addSnapshotListener { value, error ->
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
    }
}