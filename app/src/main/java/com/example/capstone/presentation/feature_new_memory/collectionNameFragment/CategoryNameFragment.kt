package com.example.capstone.presentation.feature_new_memory.collectionNameFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentCategoryNameBinding

class CategoryNameFragment : Fragment() {

    private lateinit var binding: FragmentCategoryNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryNameBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.nextBtn.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_categoryNameFragment_to_memoryMediaFragment)
        }
    }
}