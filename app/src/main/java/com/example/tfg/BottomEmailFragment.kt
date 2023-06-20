package com.example.tfg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.databinding.FragmentBottomEmailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BottomEmailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomEmailBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("Users")
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.btnEmail.setOnClickListener{
            val userId = currentUser?.email
            val documento = collection.document(userId.toString())

            taskViewModel.string.value = binding.email.text.toString()
            currentUser?.updateEmail(binding.email.text.toString())
            documento.update("email", binding.email.text.toString())
            Toast.makeText(requireContext(), "Email Updated", Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().signOut();
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, InicioFragment()).commit()
            dismiss()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomEmailBinding.inflate(inflater,container,false)
        return binding.root
    }
}