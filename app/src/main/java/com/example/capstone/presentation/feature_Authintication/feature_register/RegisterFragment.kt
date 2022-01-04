package com.example.capstone.presentation.feature_Authintication.feature_register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.RegisterFragmentBinding
import com.example.capstone.utils.isValidEmail
import com.example.capstone.utils.isValidPassword
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RegisterFragment"
@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {

    private val viewModel by viewModels<RegisterViewModel>()
    val state by lazy { viewModel.state.value }

    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = RegisterFragmentBinding.inflate(layoutInflater)
        binding.registerProgressBar.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRegisterState()
    }

    override fun onStart() {
        super.onStart()
        binding.registerBtn.setOnClickListener(this)
        binding.goToLoginClickableTxtView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.registerBtn -> {
                handleRegister()
            }
            binding.goToLoginClickableTxtView -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun handleRegister() {
        val name = binding.registerNameEdtTxt.text.toString()
        val email = binding.registerEmailEdtTxt.text.toString()
        val password = binding.registerPasswordEdtTxt.text.toString()
        if (checkValues()) {
            viewModel.handleRegister(name, email, password)
        }
    }

    private fun observeRegisterState(){
        viewModel.state.observe(viewLifecycleOwner){ result ->
            when{
                result.result == true -> {
                    binding.registerProgressBar.visibility = View.GONE
                    findNavController().popBackStack()
                }
                result.isLoading -> {
                    binding.registerProgressBar.visibility = View.VISIBLE
                }
                result.error.isNotEmpty() -> {
                    binding.registerProgressBar.visibility = View.GONE
                    showSnackBar(result.error)
                }
            }
        }
    }

    private fun checkValues(): Boolean {
        val name = binding.registerNameEdtTxt.text.toString()
        val email = binding.registerEmailEdtTxt.text.toString()
        val password = binding.registerPasswordEdtTxt.text.toString()
        val passConfirm = binding.registerConfirmPasswordEdtTxt.text.toString()
        var snackBarMessage = ""
        var isValidInputs = false
        when {
            email.isNullOrEmpty() || !email.isValidEmail()-> {
                snackBarMessage = "please enter a valid email address."
                isValidInputs = false
            }
            name.isNullOrEmpty() -> {
                snackBarMessage = "Please enter your name."
                isValidInputs = false
            }
            password.isNullOrEmpty() || password.isValidPassword() -> {
                snackBarMessage = "Password cannot be empty."
                isValidInputs = false
            }
            passConfirm.isNullOrEmpty() -> {
                snackBarMessage = "Password confirmation cannot be empty."
                isValidInputs = false
            }
            !password.contentEquals(passConfirm) -> {
                snackBarMessage = "Passwords doesn't match."
                isValidInputs = false
            }
            else -> {
                isValidInputs = true
            }
        }

        return if (isValidInputs) {
            isValidInputs
        } else {
            showSnackBar(snackBarMessage)
            isValidInputs
        }
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackBar.setAction(R.string.snackBar_dismiss_label) {
            snackBar.dismiss()
        }
        snackBar.show()
    }
}