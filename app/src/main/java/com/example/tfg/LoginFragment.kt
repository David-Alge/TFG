package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.tfg.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var db: FirebaseFirestore
    private  var userEmail: String = ""
    private  var userName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.GONE

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = requireActivity() as? MainActivity
        var userId: String = "ID"
        val currentUser: FirebaseUser? = firebaseAuth.currentUser

        binding.btnLogin.setOnClickListener {
            email = binding.txtEmail
            password = binding.txtPassword
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email.text.trim().toString(), password.text.trim().toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userId = currentUser?.uid.toString()
                        val userDocumentRef: DocumentReference = db.collection("Users").document(email.text.trim().toString())

                        userDocumentRef.get().addOnSuccessListener { snapshot ->


                            activity?.let {
                                if (email.text.trim().toString().contains("@admin.com")) {
                                    it.supportFragmentManager.beginTransaction()
                                        .replace(R.id.mainContainer, AdminFragment(), "AdminFragment")
                                        .addToBackStack("AdminFragment")
                                        .commit()
                                }else{
                                    it.supportFragmentManager.beginTransaction()
                                        .replace(R.id.mainContainer, HomeFragment(), "HomeFragment")
                                        .addToBackStack("HomeFragment")
                                        .commit()
                                }

                            }
                            email.setText("")
                            password.setText("")
                        }.addOnFailureListener { e ->
                            Log.e("DatosUser", "Error al obtener los datos del usuario", e)
                        }
                    } else {
                        Log.e("login", "fallo en inicio de sesion")
                        Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Log.e("login", "hay campos vacios")
                Toast.makeText(requireActivity(), "Empty fields are not allowed", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction().replace(R.id.mainContainer, RegisterFragment()).commit()
            }
            binding.txtEmail.setText("")
            binding.txtPassword.setText("")

        }
    }
}
