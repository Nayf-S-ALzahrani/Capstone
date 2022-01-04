package com.example.capstone.presentation.feature_Authintication.feature_register

data class RegisterState(
    val isLoading:Boolean = false,
    val result : Boolean? = null ,
    val error : String = ""
)