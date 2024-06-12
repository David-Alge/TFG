package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tfg.databinding.FragmentAddProductBinding
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
    private lateinit var Category: TextInputEditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun clearCamps() {
        Id.setText("")
        Name.setText("")
        Charact1.setText("")
        Charact2.setText("")
        Charact3.setText("")
        Price.setText("")
        Description.setText("")
        Image.setText("")
        Category.setText("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()


        Id = binding.txIdProduct
        Name = binding.txtName
        Charact1 = binding.txtCharact1
        Charact2 = binding.txtCharact2
        Charact3 = binding.txtCharact3
        Price = binding.txtPrice
        Description = binding.txtDescription
        Image = binding.txtImage
        Category = binding.txtCategoryProduct



        binding.btnAdd.setOnClickListener{
            if (Id.text?.isNotEmpty() == true && Name.text?.isNotEmpty() == true && Charact1.text?.isNotEmpty() == true
                && Charact2.text?.isNotEmpty() == true && Charact3.text?.isNotEmpty() == true && Price.text?.isNotEmpty() == true && Description.text?.isNotEmpty() == true && Image.text?.isNotEmpty() == true){
                val productData = hashMapOf(
                    "Id" to Id.text.toString(),
                    "Name" to Name.text.toString(),
                    "Charact1" to Charact1.text.toString(),
                    "Charact2" to Charact2.text.toString(),
                    "Charact3" to Charact3.text.toString(),
                    "Price" to Price.text.toString(),
                    "Description" to Description.text.toString(),
                    "Image" to Image.text.toString(),
                    "Category" to Category.text.toString()

                )
                val idProduct = Id.text.toString()
                val documentRef = db.collection("Products").document(idProduct)
                documentRef.set(productData).addOnSuccessListener {
                    if (isAdded) { // Verificar si el fragmento está adjunto
                        Toast.makeText(requireActivity(), "Product added", Toast.LENGTH_LONG).show()
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.mainContainer, AdminProductsFragment())
                        transaction.commit()
                        clearCamps()
                    }

                }
                    .addOnFailureListener { e ->
                        Log.e("register", "Error al crear el documento de usuario", e)
                        Toast.makeText(requireContext(), "Error creating user", Toast.LENGTH_LONG).show()
                    }
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