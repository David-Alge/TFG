package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.databinding.FragmentAdminProductBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore


class AdminProductFragment : Fragment() {
    private lateinit var binding: FragmentAdminProductBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var productsArrayList: ArrayList<Products>
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: AdminProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.VISIBLE
        val mainActivity = requireActivity() as MainActivity
        val actionBarDrawerToggle = mainActivity.getDrawerToggle()
        actionBarDrawerToggle?.isDrawerIndicatorEnabled = false

        db = FirebaseFirestore.getInstance()

        productsArrayList = ArrayList()
        recyclerView = binding.ListaProducts
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        myAdapter = AdminProductAdapter(productsArrayList){products ->
            activity?.let {
                val fragment = ProductAdminFragment()
                val bundle = Bundle()

                bundle.putString("id", products.Id)
                Log.d("id", products.Id)
                fragment.arguments = bundle
                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.mainContainer, fragment).commit()
            }

        }
        recyclerView.adapter = myAdapter
        EventChangeListener()

    }
    private fun EventChangeListener() {
        val productsCollectionRef = db.collection("Products")

        productsCollectionRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot = task.result
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    recyclerView.visibility = View.VISIBLE
                    productsCollectionRef.addSnapshotListener { value, error ->
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
                }
            }
        }
    }

}