package com.example.capstone.presentation.feature_Authintication.feature_register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.capstone.domain.use_case.GetCurrentUserUseCase
import com.example.capstone.domain.use_case.RegisterUseCase
import com.example.capstone.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state  = MutableLiveData(RegisterState())

    val state: LiveData<RegisterState> = _state

    fun handleRegister(name: String, email: String, password: String){
        registerUseCase(name, email, password).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = RegisterState(
                        result = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = RegisterState(
                        error = result.message ?: "something went wrong"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}