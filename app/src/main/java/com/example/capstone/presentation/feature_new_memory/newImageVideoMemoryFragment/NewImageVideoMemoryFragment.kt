package com.example.capstone.presentation.feature_new_memory.newImageVideoMemoryFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstone.R
import com.example.capstone.databinding.NewImageVideoMemoryFragmentBinding

private const val TAG = "NewMemoryFragment"

class NewImageVideoMemoryFragment : Fragment() {


    private val viewModel by lazy {
        ViewModelProvider(this)[NewImageVideoMemoryViewModel::class.java]
    }

    private lateinit var binding: NewImageVideoMemoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewImageVideoMemoryFragmentBinding.inflate(layoutInflater)
        binding.previousBtn.visibility = View.INVISIBLE
        binding.nextBtn.visibility = View.INVISIBLE

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.uploadMemoryImgView.setOnClickListener {
            binding.flipper.showNext()
            binding.nextBtn.visibility = View.VISIBLE
        }
        binding.previousBtn.setOnClickListener {

            if (binding.flipper.currentView.id == R.id.memoryDetailTitleAndDate) {
                binding.nextBtn.visibility = View.INVISIBLE
            }
            binding.flipper.showPrevious()
        }
        binding.nextBtn.setOnClickListener {
            binding.nestedFlipper.showNext()
        }
    }
}