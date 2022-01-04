package com.example.capstone.presentation.feature_Authintication.feature_Login

data class LoginState(
    val isLoading:Boolean = false,
    val result : Boolean? = null ,
    val error : String = ""
)
