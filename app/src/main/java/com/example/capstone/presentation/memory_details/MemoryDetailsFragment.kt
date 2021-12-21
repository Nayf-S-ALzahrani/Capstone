package com.example.capstone.presentation.memory_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstone.R

class MemoryDetailsFragment : Fragment() {

    private val memoryDetailsViewModel by lazy{
        ViewModelProvider(this)[MemoryDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.memory_details_fragment, container, false)
    }

}