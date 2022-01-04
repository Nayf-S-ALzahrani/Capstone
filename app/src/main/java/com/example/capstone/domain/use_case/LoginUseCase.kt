package com.example.capstone.domain.use_case

import com.example.capstone.domain.repository.CapstoneRepository
import com.example.capstone.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: CapstoneRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.handleLogin(email, password)
            emit(Resource.Success(response))
            if (response){
                emit(Resource.Success(response))
            }else{
                throw IllegalStateException("false")
            }
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error" ))

        }catch (e: IOException){
            emit(Resource.Error("Can't reach the server plz check the internet connection" ))
        }catch (e: IllegalStateException){
            emit(Resource.Error(e.message))
        }
    }
}