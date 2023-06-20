package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.databinding.FragmentBottomNameBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class BottomNameFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomNameBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val activity = requireActivity()
            val db = FirebaseFirestore.getInstance()
            val collection = db.collection("Users")

            val firebaseAuth = FirebaseAuth.getInstance()
            val currentUser = firebaseAuth.currentUser

            taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
            binding.btnnuevoNombre.setOnClickListener{
                val userId = currentUser?.email
                val documento = collection.document(userId.toString())

                taskViewModel.string.value = binding.name.text.toString()

                documento.update("firstName", binding.name.text.toString())
                Toast.makeText(requireContext(), "First Name Updated", Toast.LENGTH_SHORT).show()
                dismiss()
            }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNameBinding.inflate(inflater,container,false)
        return binding.root
    }
}