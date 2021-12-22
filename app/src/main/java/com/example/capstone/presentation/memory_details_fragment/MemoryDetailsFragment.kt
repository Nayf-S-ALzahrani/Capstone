package com.example.capstone.presentation.memory_details_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.MemoryDetailsFragmentBinding

class MemoryDetailsFragment : Fragment(), View.OnClickListener {

    private val viewModel by lazy{
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

    override fun onStart() {
        super.onStart()
        binding.addCommentBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.addCommentBtn -> {
                val navController = findNavController()
                navController.navigate(R.id.action_memoryDetailsFragment_to_addCommentFragment)
            }
        }
    }
}