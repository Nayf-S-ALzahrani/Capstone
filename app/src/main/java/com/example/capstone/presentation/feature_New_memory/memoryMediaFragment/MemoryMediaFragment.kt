package com.example.capstone.presentation.feature_New_memory.memoryMediaFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.utils.Constants
import com.example.capstone.databinding.FragmentMemoryMediaBinding

private const val TAG = "MemoryMediaFragment"

class MemoryMediaFragment : Fragment() {

    private lateinit var binding: FragmentMemoryMediaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemoryMediaBinding.inflate(layoutInflater)
//        Log.d(TAG, "onCreateView: ${arguments?.getString("categoryName")}")
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.imageMemoryImgView.setOnClickListener {
            val mediaType = bundleOf("mediaType" to Constants.IMAGE_MEMORY)
            findNavController().navigate(R.id.action_memoryMediaFragment_to_newImageVideoMemoryFragment, mediaType)
        }
        binding.videoMemoryImgView.setOnClickListener {
            val mediaType = bundleOf("mediaType" to Constants.VIDEO_MEMORY)
            findNavController().navigate(R.id.action_memoryMediaFragment_to_newImageVideoMemoryFragment, mediaType)
        }
        binding.voiceNoteMemoryImgView.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_memoryMediaFragment_to_newVoiceNoteMemoryFragment)
        }
    }
}