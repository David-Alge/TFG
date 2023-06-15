package com.example.tfg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.databinding.FragmentBottomEmailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomEmailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomEmailBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.btnEmail.setOnClickListener{
            saveName()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomEmailBinding.inflate(inflater,container,false)
        return binding.root
    }


    private fun saveName() {
        taskViewModel.name.value = binding.email.text.toString()
        Toast.makeText(requireContext(), binding.email.text.toString(), Toast.LENGTH_SHORT).show()
        dismiss()

    }

}