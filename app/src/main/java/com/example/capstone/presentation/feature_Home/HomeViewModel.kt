package com.example.capstone.presentation.feature_Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.domain.models.Memory
import com.example.capstone.domain.repository.CapstoneRepository
import com.example.capstone.domain.use_case.GetCurrentUserUseCase
import com.example.capstone.domain.use_case.LogoutUseCase
import com.example.capstone.domain.use_case.RegisterUseCase
import com.example.capstone.presentation.feature_Authintication.feature_Login.LoginState
import com.example.capstone.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

//    init {
//        getCurrentUser()
//    }
    private val _state = MutableLiveData(UserState())
    val state: LiveData<UserState> = _state

    fun getCurrentUser(){
        getCurrentUserUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = UserState(currentUser = result.data)
                }
                is Resource.Error -> {
                    _state.value = UserState(error = result.message ?: "something went wrong")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logout() {
        logoutUseCase.invoke()
    }

    val memories = mutableListOf<Memory>(
        Memory(title = "test 1", type = "image"),
        Memory(title = "test 2", type = "voice_note"),
        Memory(title = "test 3", type = "video"),
        Memory(title = "test 4", type = "voice_note"),
        Memory(title = "test 5", type = "image")
    )

}