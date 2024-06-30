package com.dicoding.mystoryapp.di

import android.content.Context
import com.dicoding.mystoryapp.data.api.ApiConfig
import com.dicoding.mystoryapp.data.preference.UserPreference
import com.dicoding.mystoryapp.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object InjectionModule {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository(apiService,pref)
    }
}