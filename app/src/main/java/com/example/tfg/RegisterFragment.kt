package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class RegisterFragment : Fragment() {

    private lateinit var btnRegister: Button
    private lateinit var btnCancel: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var name: EditText
    private lateinit var lastname: EditText
    private lateinit var address: EditText
    private lateinit var db: FirebaseFirestore

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userId: String = "ID"
        val add = HashMap<String,Any>()

        btnRegister = view.findViewById(R.id.btnRegister)
        btnCancel = view.findViewById(R.id.btnCancel)
        email = view.findViewById(R.id.txtEmail)
        password = view.findViewById(R.id.txtPassword)
        name = view.findViewById(R.id.txtFirstname)
        lastname = view.findViewById(R.id.txtLastname)
        address = view.findViewById(R.id.txtAddres)


        btnRegister.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty() && name.text.isNotEmpty() && lastname.text.isNotEmpty()&& address.text.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email.text.trim().toString(), password.text.trim().toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        firebaseAuth.signInWithEmailAndPassword(email.text.trim().toString(), password.text.trim().toString())
                        val currentUser: FirebaseUser? = firebaseAuth.currentUser
                        if (currentUser != null) {
                             userId = currentUser.uid
                        }
                        add["firstName"] = name.text.toString()
                        add["lastName"] = lastname.text.toString()
                        add["email"] = email.text.toString()
                        add["password"] = password.text.toString()
                        add["address"] = address.text.toString()
                        add["Id"] = userId
                        db.collection("Users").add(add)
                        Toast.makeText(requireContext(), "New user created", Toast.LENGTH_LONG).show()
                        val transaccion = requireActivity().supportFragmentManager.beginTransaction()
                        transaccion.replace(R.id.mainContainer, InicioFragment())
                        transaccion.commit()

                    }else{
                        Log.e("register","fallo en creacion de usuario")
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                Log.e("register","hay campos vacios")
                Toast.makeText(requireActivity() , "Empty fields are not allowed", Toast.LENGTH_LONG).show();
            }
        }
        btnCancel.setOnClickListener {
            val transaccion = requireActivity().supportFragmentManager.beginTransaction()
            transaccion.replace(R.id.mainContainer, InicioFragment())
            transaccion.commit()
        }
    }

}