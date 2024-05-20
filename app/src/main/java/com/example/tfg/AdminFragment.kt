package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {
    private lateinit var binding: FragmentAdminBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsArrayList: ArrayList<Users>
    private lateinit var myAdapter: AdminAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }


}