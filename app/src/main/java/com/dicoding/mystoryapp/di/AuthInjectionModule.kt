package com.dicoding.mystoryapp.di

import android.content.Context
import com.dicoding.mystoryapp.data.api.AuthConfig
import com.dicoding.mystoryapp.data.preference.UserPreference
import com.dicoding.mystoryapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object AuthInjectionModule {
    fun provideAuth (context: Context) : AuthRepository {
        val userPreference = UserPreference.getInstance(context)
        val user = runBlocking { userPreference.getSession().first()}
        val authApiService = AuthConfig.getApiService(user.token)
        return AuthRepository(authApiService, userPreference)
    }
}