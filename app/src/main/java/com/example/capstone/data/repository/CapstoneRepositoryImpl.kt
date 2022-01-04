package com.example.capstone.data.repository

import com.example.capstone.domain.models.User
import com.example.capstone.domain.repository.CapstoneRepository
import com.example.capstone.utils.Constants
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "CapstoneRepositoryImpl"

open class CapstoneRepositoryImpl() : CapstoneRepository {

    override var auth = Firebase.auth
    override var firestore = Firebase.firestore

    override suspend fun handleLogin(email: String, password: String): Boolean {
        var isSuccessful = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isSuccessful = true
            }.addOnFailureListener {
                isSuccessful = false
            }.await()

        return isSuccessful
    }

    override suspend fun handleRegister(name: String, email: String, password: String): Boolean {
        var isSuccessful = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = User(
                    id = auth.currentUser!!.uid,
                    name = name
                )
                firestore.collection(Constants.USERS_COLLECTION)
                    .document(auth.currentUser!!.uid)
                    .set(user)
                isSuccessful = true
            }.addOnFailureListener {
                isSuccessful = false
            }.await()

        return isSuccessful
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun handleLogout() {
        auth.signOut()
    }
}