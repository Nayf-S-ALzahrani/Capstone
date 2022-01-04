package com.example.capstone.presentation.feature_Home

import com.google.firebase.auth.FirebaseUser

data class UserState(
    val currentUser: FirebaseUser? = null,
    val error: String = ""
)
