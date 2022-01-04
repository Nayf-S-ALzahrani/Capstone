package com.example.capstone.presentation.feature_Authintication.feature_Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.domain.use_case.LoginUseCase
import com.example.capstone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _state = MutableLiveData(LoginState())
    val state: LiveData<LoginState> = _state

    fun handleLogin(email: String, password: String){
        loginUseCase(email, password).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = LoginState(result = result.data)
                }
                is Resource.Error -> {
                    _state.value = LoginState(error = result.message ?: "something went wrong")
                }
            }
        }.launchIn(viewModelScope)
    }
}