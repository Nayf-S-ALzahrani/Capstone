package com.example.capstone.domain.use_case

import com.example.capstone.domain.repository.CapstoneRepository
import com.example.capstone.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repo: CapstoneRepository
)  {
    operator fun invoke(): Flow<Resource<FirebaseUser?>> = flow {
        try {
            val response = repo.getCurrentUser()
            emit(Resource.Success(response))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error" ))

        }catch (e: IOException){
            emit(Resource.Error("Can't reach the server plz check the internet connection" ))
        }
    }
}