package com.dicoding.mystoryapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mystoryapp.repository.AuthRepository
import com.dicoding.mystoryapp.di.AuthInjectionModule
import com.dicoding.mystoryapp.view.signin.SigninViewModel
import com.dicoding.mystoryapp.view.singup.SignupViewModel

class AuthViewModelFactory(private val repo: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SigninViewModel::class.java) -> {
                SigninViewModel(repo) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Tidak Menemukan ViewModel" + modelClass::class.java)
        }
    }
    companion object {
        fun getInstance(context: Context) = AuthViewModelFactory(AuthInjectionModule.provideAuth(context))
    }
}