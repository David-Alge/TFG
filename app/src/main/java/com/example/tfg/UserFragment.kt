package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.tfg.databinding.FragmentProductBinding
import com.example.tfg.databinding.FragmentUserBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class UserFragment : Fragment() {
    private lateinit var userID: String
    private lateinit var binding: FragmentUserBinding
    private lateinit var fName: TextInputEditText
    private lateinit var lName: TextInputEditText
    private lateinit var email: TextView
    private lateinit var address: TextInputEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userID = arguments?.getString("id").toString()

        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        val documentRef = db.collection("Users").document("$userID")


        fName = binding.txtFirstname
        lName = binding.txtLastname
        email=binding.txtEmail
        address= binding.txtAddres

        documentRef.get().addOnSuccessListener { documentSnapshot ->
            fName.setHint(documentSnapshot.getString("firstName"))
            lName.setHint(documentSnapshot.getString("lastName"))
            email.setText(documentSnapshot.getString("email"))
            address.setHint(documentSnapshot.getString("address"))
        }

        binding.btnModify.setOnClickListener{
            if (fName.text?.isNotEmpty()!!){
                val fNameUpdate = fName.text.toString()
                documentRef.update("firstName",fNameUpdate)
                Log.d("fNameUpdate",fNameUpdate)
            }
            if (lName.text?.isNotEmpty()!!){
                val lNameUpdate = lName.text.toString()
                documentRef.update("lastName",lNameUpdate)
                Log.d("lNameUpdate",lNameUpdate)
            }

            if (email.text?.isNotEmpty()!!){
                val emailUpdate = email.text.toString()
                documentRef.update("email",emailUpdate)
                Log.d("emailUpdate",emailUpdate)
            }
            Toast.makeText(requireActivity() , "User modified", Toast.LENGTH_LONG).show();
            val transaccion = requireActivity().supportFragmentManager.beginTransaction()
            transaccion.replace(R.id.mainContainer, AdminFragment())
            transaccion.commit()
        }

        binding.btnCancel.setOnClickListener{
            val transaccion = requireActivity().supportFragmentManager.beginTransaction()
            transaccion.replace(R.id.mainContainer, AdminFragment())
            transaccion.commit()
        }

    }

}