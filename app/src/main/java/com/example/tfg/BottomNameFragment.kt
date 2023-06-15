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


class BottomNameFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomNameBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.btnnuevoNombre.setOnClickListener{
            saveName()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNameBinding.inflate(inflater,container,false)
        return binding.root
    }


    private fun saveName() {
        taskViewModel.name.value = binding.name.text.toString()
        Toast.makeText(requireContext(), binding.name.text.toString(), Toast.LENGTH_SHORT).show()
        dismiss()

    }

}