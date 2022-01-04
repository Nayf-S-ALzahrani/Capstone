package com.example.capstone.di

import com.example.capstone.data.repository.CapstoneRepositoryImpl
import com.example.capstone.domain.repository.CapstoneRepository
import com.example.capstone.domain.use_case.LoginUseCase
import com.example.capstone.domain.use_case.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(): CapstoneRepository{
        return CapstoneRepositoryImpl()
    }
}