package com.example.tfg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.databinding.FragmentBottomPasswordBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BottomPasswordFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomPasswordBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("Users")
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        binding.btnContrasena.setOnClickListener{
            val userId = currentUser?.uid
            val documento = collection.document(userId.toString())
            taskViewModel.string.value = binding.passwordO.text.toString()

            if (binding.passwordC.text.toString().equals(binding.passwordN.text.toString())){
                documento.update("password", binding.passwordO.text.toString())
                currentUser?.updatePassword(binding.passwordO.text.toString())
                Toast.makeText(requireContext(), "Password Updated", Toast.LENGTH_SHORT).show()
                dismiss()
                FirebaseAuth.getInstance().signOut();
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, LoginFragment()).commit()
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }




}