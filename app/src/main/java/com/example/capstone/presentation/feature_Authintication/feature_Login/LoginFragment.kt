package com.example.capstone.presentation.feature_Authintication.feature_Login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LoginFragment"
@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private val viewModel by viewModels<LoginViewModel>()
    val state by lazy { viewModel.state.value }

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        binding.loginProgressBar.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginState()
    }
    override fun onStart() {
        super.onStart()
        binding.loginBtn.setOnClickListener(this)
        binding.signUpClickableTxtView.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v){
            binding.loginBtn -> {
                handleLogin()
            }
            binding.signUpClickableTxtView -> {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun handleLogin() {
        val email = binding.loginEmailEdtTxt.text.toString()
        val password = binding.loginPasswordEdtTxt.text.toString()
        if (checkValues()) {
            viewModel.handleLogin(email, password)
        }
    }

    private fun observeLoginState(){
        viewModel.state.observe(viewLifecycleOwner){ result ->
            when{
                result.result == true -> {
                    binding.loginProgressBar.visibility = View.GONE
                    findNavController().popBackStack()
                }
                result.isLoading -> {
                    binding.loginProgressBar.visibility = View.VISIBLE
                }
                result.error.isNotEmpty() -> {
                    binding.loginProgressBar.visibility = View.GONE
                    showSnackBar(result.error)
                }
            }
        }
    }

    private fun checkValues(): Boolean {
        val email = binding.loginEmailEdtTxt.text.toString()
        val password = binding.loginPasswordEdtTxt.text.toString()
        var snackBarMessage = ""
        var isValidInputs = false
        when {
            email.isNullOrEmpty() -> {
                snackBarMessage = "please enter your email address."
                isValidInputs = false
            }
            password.isNullOrEmpty() -> {
                snackBarMessage = "Password cannot be empty."
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