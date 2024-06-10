package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.databinding.FragmentAdminProductBinding
import com.google.firebase.firestore.FirebaseFirestore


class ProductModifyFragment : Fragment() {
    private lateinit var productID: String
    private lateinit var binding: FragmentAdminProductBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productID = arguments?.getString("id").toString()

        // Inflate the layout for this fragment
        binding = FragmentAdminProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        val documentRef = db.collection("Products").document("$productID")




    }


}