package com.example.capstone.domain.use_case

import android.util.Log
import com.example.capstone.domain.repository.CapstoneRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "LogoutUseCase"
class LogoutUseCase @Inject constructor(
    private val repo: CapstoneRepository
) {
    operator fun invoke(){
        CoroutineScope(IO).launch {
            repo.handleLogout()
        }
        Log.d(TAG, "LogoutUseCase: Logged out successfully")
    }
}