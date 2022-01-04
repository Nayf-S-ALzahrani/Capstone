package com.example.capstone.presentation.feature_New_memory.collectionNameFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentCategoryNameBinding
import com.google.android.material.snackbar.Snackbar

class CategoryNameFragment : Fragment() {

    private lateinit var binding: FragmentCategoryNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryNameBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.root.setOnClickListener {
            binding.collectionNameTxtInput.clearFocus()
        }
        binding.nextBtn.setOnClickListener {
            if (binding.collectionNameTxtInput.text.isNullOrEmpty()){
                val snackBar = Snackbar.make(requireView(), R.string.collection_name_empty_snackBar_msg, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.snackBar_dismiss_label) {
                    snackBar.dismiss()
                }
                snackBar.show()
            }else {
                val navController = findNavController()
                val categoryName = bundleOf("categoryName" to binding.collectionNameTxtInput.text.toString())
                navController.navigate(R.id.action_categoryNameFragment_to_memoryMediaFragment, categoryName)
            }
        }
    }
}