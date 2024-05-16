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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.tfg.databinding.FragmentProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ProductFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var binding: FragmentProductBinding
    private lateinit var imagen: ImageView
    private lateinit var productName: TextView
    private lateinit var precio: TextView
    private lateinit var category: TextView
    private lateinit var charact1: TextView
    private lateinit var charact2: TextView
    private lateinit var charact3: TextView
    private lateinit var productID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productID = arguments?.getString("ID").toString()
        val view = inflater.inflate(R.layout.activity_main, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
        val mainActivity = requireActivity() as MainActivity
        val actionBarDrawerToggle = mainActivity.getDrawerToggle()
        actionBarDrawerToggle?.setDrawerIndicatorEnabled(false)

        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        val documentRef = db.collection("Products").document("$productID")
        val user = FirebaseAuth.getInstance()
        val userId = user.currentUser?.email

        val cartCollectionRef = db.collection("Users").document(userId.toString())
            .collection("Cart" + userId.toString())
        val productRef = FirebaseFirestore.getInstance().collection("Products").document(productID)

        imagen = binding.imgProduct
        productName = binding.nameProduct
        precio = binding.priceProduct
        category = binding.categoryProduct
        charact1 = binding.charact1Product
        charact2 = binding.charact2Product
        charact3 = binding.charact3Product

        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                productName.setText(documentSnapshot.getString("Name"))
                Picasso.get().load(documentSnapshot.getString("Img")).into(imagen)
                precio.setText(documentSnapshot.getString("Price"))
                category.setText(documentSnapshot.getString("Category"))
                charact1.setText(documentSnapshot.getString("Charact1"))
                charact2.setText(documentSnapshot.getString("Charact2"))
                charact3.setText(documentSnapshot.getString("Charact3"))
            }


        binding.btnAdd.setOnClickListener {
            productRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Carrito", "$userId")
                    val data = document.data
                    Toast.makeText(
                        binding.btnAdd.context,
                        "Product added to cart",
                        Toast.LENGTH_SHORT
                    ).show();

                    if (data != null) {
                        cartCollectionRef.add(data)
                    }
                }
            }
        }
    }
}