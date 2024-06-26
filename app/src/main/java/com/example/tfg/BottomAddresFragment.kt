package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.databinding.FragmentBottomAddresBinding
import com.example.tfg.databinding.FragmentBottomEmailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class BottomAddresFragment  : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomAddresBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("Users")

        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.btnDireccion.setOnClickListener{
            val userId = currentUser?.uid
            val documento = collection.document(userId.toString())

            taskViewModel.string.value = binding.addressO.text.toString()

            if (binding.addressO.text.toString().equals(binding.addressN.text.toString())){
                documento.update("address", binding.addressO.text.toString())
                Toast.makeText(requireContext(), "Addres Updated", Toast.LENGTH_SHORT).show()
                dismiss()
            }


        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomAddresBinding.inflate(inflater,container,false)
        return binding.root
    }

}