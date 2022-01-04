package com.example.capstone.presentation.feature_New_memory.oneOrCollectionFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentOneOrCollectionBinding

class OneOrCollectionFragment : Fragment() {

    private lateinit var binding: FragmentOneOrCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneOrCollectionBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.oneMemoryImgView.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_oneOrCollectionFragment_to_memoryMediaFragment)
        }

        binding.memoriesCollectionImgView.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_oneOrCollectionFragment_to_categoryNameFragment)
        }
    }
}