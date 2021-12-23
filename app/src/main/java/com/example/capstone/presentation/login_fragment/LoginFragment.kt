package com.example.capstone.presentation.login_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstone.R
import com.example.capstone.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private lateinit var binding: LoginFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

}