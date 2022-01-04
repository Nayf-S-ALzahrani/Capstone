package com.example.capstone.presentation.feature_Syncing_jewllery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstone.databinding.SyncingFragmentBinding

class SyncingFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[SyncingViewModel::class.java]
    }

    private lateinit var binding: SyncingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SyncingFragmentBinding.inflate(layoutInflater)


        return binding.root
    }

}