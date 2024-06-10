package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tfg.databinding.FragmentAddProductBinding
import com.example.tfg.databinding.FragmentProductAdminBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore


class AddProductFragment : Fragment() {
    private lateinit var productID: String
    private lateinit var binding: FragmentAddProductBinding
    private lateinit var Id: TextInputEditText
    private lateinit var Name: TextInputEditText
    private lateinit var Charact1: TextInputEditText
    private lateinit var Charact2: TextInputEditText
    private lateinit var Charact3: TextInputEditText
    private lateinit var Price: TextInputEditText
    private lateinit var Description: TextInputEditText
    private lateinit var Image: TextInputEditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        val documentRef = db.collection("Products").document("$productID")

        Id = binding.txIdProduct
        Name = binding.txtName
        Charact1 = binding.txtCharact1
        Charact2 = binding.txtCharact2
        Charact3 = binding.txtCharact3
        Price = binding.txtPrice
        Description = binding.txtDescription
        Image = binding.txtImage

        documentRef.get().addOnSuccessListener { documentSnapshot ->
            Id.setHint(documentSnapshot.getString("Id"))
            Name.setHint(documentSnapshot.getString("Name"))
            Charact1.setHint(documentSnapshot.getString("Charact1"))
            Charact2.setHint(documentSnapshot.getString("Charact2"))
            Charact3.setHint(documentSnapshot.getString("Charact3"))
            Price.setHint(documentSnapshot.getString("Price"))
            Description.setHint(documentSnapshot.getString("Description"))
            Image.setHint(documentSnapshot.getString("Img"))
        }

        binding.btnAdd.setOnClickListener{
            if (Id.text?.isNotEmpty() == true && Name.text?.isNotEmpty() == true){
                val IdUpdate = Id.text.toString()
                documentRef.update("Id",IdUpdate)
                Log.d("Id",IdUpdate)
            }
            if (Name.text?.isNotEmpty()!!){
                val NameUpdate = Name.text.toString()
                documentRef.update("Name",NameUpdate)
                Log.d("Name",NameUpdate)
            }
            if (Charact1.text?.isNotEmpty()!!){
                val Charact1Update = Charact1.text.toString()
                documentRef.update("Charact1",Charact1Update)
                Log.d("Charact1",Charact1Update)
            }
            if (Charact2.text?.isNotEmpty()!!){
                val Charact2Update = Charact2.text.toString()
                documentRef.update("Charact2",Charact2Update)
                Log.d("Charact2",Charact2Update)
            }
            if (Charact3.text?.isNotEmpty()!!){
                val Charact3Update = Charact3.text.toString()
                documentRef.update("Charact3",Charact3Update)
                Log.d("Charact3",Charact3Update)
            }
            if (Price.text?.isNotEmpty()!!){
                val PriceUpdate = Price.text.toString()
                documentRef.update("Price",PriceUpdate)
                Log.d("Price",PriceUpdate)
            }
            if (Description.text?.isNotEmpty()!!){
                val DescriptionUpdate = Description.text.toString()
                documentRef.update("Description",DescriptionUpdate)
                Log.d("Description",DescriptionUpdate)
            }
            if (Image.text?.isNotEmpty()!!){
                val ImageUpdate = Image.text.toString()
                documentRef.update("Img",ImageUpdate)
                Log.d("Img",ImageUpdate)
            }
            Toast.makeText(requireActivity() , "User modified", Toast.LENGTH_LONG).show();
            val transaccion = requireActivity().supportFragmentManager.beginTransaction()
            transaccion.replace(R.id.mainContainer, AdminProductsFragment())
            transaccion.commit()
        }

        binding.btnCancel
            .setOnClickListener {
                val transaccion = requireActivity().supportFragmentManager.beginTransaction()
                transaccion.replace(R.id.mainContainer, AdminProductsFragment())
                transaccion.commit()
            }
    }

}