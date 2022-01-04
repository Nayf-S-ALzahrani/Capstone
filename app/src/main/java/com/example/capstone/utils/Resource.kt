package com.example.capstone.utils

import com.example.capstone.data.repository.CapstoneRepositoryImpl
import com.example.capstone.domain.repository.CapstoneRepository

sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message)
    class Loading<T>(data: T? = null): Resource<T>(data)
}
