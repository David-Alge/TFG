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
import com.google.firebase.firestore.DocumentReference


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
                            firebaseAuth.signOut();
                        }
                        val userData = hashMapOf(
                            "firstName" to name.text.toString(),
                            "lastName" to lastname.text.toString(),
                            "email" to email.text.toString(),
                            "password" to password.text.toString(),
                            "address" to address.text.toString(),
                            "Id" to userId
                        )
                        val userDocumentRef: DocumentReference = db.collection("Users").document(email.text.toString())

                        userDocumentRef.set(userData)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Documento agregado con éxito con ID: $userId")
                                Toast.makeText(requireContext(), "New user created", Toast.LENGTH_LONG).show()
                                val transaccion = requireActivity().supportFragmentManager.beginTransaction()
                                transaccion.replace(R.id.mainContainer, InicioFragment())
                                transaccion.commit()
                                clearCamps()
                            }
                            .addOnFailureListener { e ->
                                Log.e("register", "Error al crear el documento de usuario", e)
                                Toast.makeText(requireContext(), "Error creating user", Toast.LENGTH_LONG).show()
                            }
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
    private fun clearCamps() {
        email.setText("")
        password.setText("")
        name.setText("")
        lastname.setText("")
        address.setText("")
    }

}