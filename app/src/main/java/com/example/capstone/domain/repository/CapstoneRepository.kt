package com.example.capstone.domain.repository

import com.example.capstone.domain.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface CapstoneRepository {

    var auth: FirebaseAuth
    var firestore: FirebaseFirestore

    suspend fun handleLogin(email: String, password: String): Boolean

    suspend fun handleRegister(name: String, email: String, password: String): Boolean

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun handleLogout()
}