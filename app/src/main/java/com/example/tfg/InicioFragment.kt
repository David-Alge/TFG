package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.tfg.databinding.ActivityMainBinding
import com.example.tfg.databinding.FragmentInicioBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView?.visibility = View.GONE


        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE

        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            activity?.let {
                it.supportFragmentManager.beginTransaction().replace(R.id.mainContainer, HomeFragment()).commit()
            }
        }
        binding.btnRegister.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction().replace(R.id.mainContainer, RegisterFragment()).commit()
            }
        }
    }
}
