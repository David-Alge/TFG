package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.databinding.FragmentAdminBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class AdminFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentAdminBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersArrayList: ArrayList<Users>
    private lateinit var myAdapter: AdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar el Toolbar

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.VISIBLE
        val mainActivity = requireActivity() as MainActivity
        val actionBarDrawerToggle = mainActivity.getDrawerToggle()
        actionBarDrawerToggle?.isDrawerIndicatorEnabled = false

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance()

        // Inicializar la lista de usuarios y el RecyclerView
        usersArrayList = ArrayList()
        recyclerView = binding.ListaUsers
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        myAdapter = AdminAdapter(usersArrayList)
        recyclerView.adapter = myAdapter

        // Escuchar cambios en Firestore
        EventChangeListener()

        // Inicializar el botón de salir
        val imgbtnSalir = view?.findViewById<ImageView>(R.id.imgbtnSalir)
        imgbtnSalir?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, LoginFragment()).commit()
        }

        val addbtn = binding.addUserbtn
        addbtn?.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, AddUserFragment()).commit()
        }

    }

    private fun deleteuser() {

    }

    private fun EventChangeListener() {
        val userCollectionRef = db.collection("Users")

        userCollectionRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot = task.result
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    recyclerView.visibility = View.VISIBLE
                    userCollectionRef.addSnapshotListener { value, error ->
                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                            return@addSnapshotListener
                        }
                        for (dc in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                val user = dc.document.toObject(Users::class.java)
                                Log.d("Firestore Data", "${user.firstName}")
                                usersArrayList.add(user)
                            }
                        }
                        myAdapter.notifyDataSetChanged()
                    }
                } else {
                    recyclerView.visibility = View.GONE
                }
            }
        }
    }
}
