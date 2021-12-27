package com.example.capstone.presentation.feature_new_memory.memoryMediaFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentMemoryMediaBinding

class MemoryMediaFragment : Fragment() {

    private lateinit var binding: FragmentMemoryMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemoryMediaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.imageMemoryImgView.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_memoryMediaFragment_to_newImageVideoMemoryFragment)
        }
        binding.videoMemoryImgView.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_memoryMediaFragment_to_newImageVideoMemoryFragment)
        }
        binding.voiceNoteMemoryImgView.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_memoryMediaFragment_to_newVoiceNoteMemoryFragment)
        }
    }
}