package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity


class ProfileFragment : Fragment() {
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
        val mainActivity = requireActivity() as MainActivity
        val actionBarDrawerToggle = mainActivity.getDrawerToggle()
        actionBarDrawerToggle?.setDrawerIndicatorEnabled(false)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnTxtPerfil = view.findViewById<Button>(R.id.btnTxtPerfil)
        btnTxtPerfil?.setOnClickListener {
            BottomNameFragment().show(parentFragmentManager, "newTaskTag")
        }
        val btnContrasena = view.findViewById<Button>(R.id.btnContraseña)
        btnContrasena?.setOnClickListener {
            BottomPasswordFragment().show(parentFragmentManager, "newTaskTag")
        }
        val btnDireccion = view.findViewById<Button>(R.id.btnDireccion)
        btnDireccion?.setOnClickListener {
            BottomAddresFragment().show(parentFragmentManager, "newTaskTag")
        }
        val btnCorreo = view.findViewById<Button>(R.id.btnCorreo)
        btnCorreo?.setOnClickListener {
            BottomEmailFragment().show(parentFragmentManager, "newTaskTag")
        }


    }


}