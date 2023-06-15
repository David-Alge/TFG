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
import com.example.tfg.databinding.ActivityMainBinding
import com.example.tfg.databinding.FragmentInicioBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.GONE

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE
        firebaseAuth = FirebaseAuth.getInstance()

        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
             email = binding.txtEmail
             password = binding.txtPassword
            if (email.text.isNotEmpty() && password.text.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email.text.trim().toString(), password.text.trim().toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        activity?.let {
                            it.supportFragmentManager.beginTransaction().replace(R.id.mainContainer, HomeFragment()).commit()
                        }

                    }else{
                        Log.e("login","fallo en inicio de sesion")
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                Log.e("login","hay campos vacios")
                Toast.makeText(requireActivity() , "Empty fields are not allowed", Toast.LENGTH_LONG).show();
            }
        }
        binding.btnRegister.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction().replace(R.id.mainContainer, RegisterFragment()).commit()
            }
        }
    }
}
