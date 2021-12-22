package com.example.capstone.presentation.memory_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstone.R
import com.example.capstone.databinding.MemoryDetailsFragmentBinding

class MemoryDetailsFragment : Fragment() {

    private val memoryDetailsViewModel by lazy{
        ViewModelProvider(this)[MemoryDetailsViewModel::class.java]
    }

    private lateinit var binding: MemoryDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MemoryDetailsFragmentBinding.inflate(layoutInflater)

        binding.memoryDetailsTitleTv.text = arguments?.getString("name")
        binding.memoryDetailsImgV.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_foreground))
        return binding.root
    }

}