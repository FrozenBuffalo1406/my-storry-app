package com.dicoding.mystoryapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mystoryapp.data.repository.UserRepository
import com.dicoding.mystoryapp.view.main.MainViewModel
import com.dicoding.mystoryapp.view.upload.UploadViewModel
import com.dicoding.mystoryapp.di.InjectionModule
import com.dicoding.mystoryapp.view.detail.DetailViewModel

class ViewModelFactory(private val repo: UserRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repo) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(repo) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Tidak menemukan ViewModel" + modelClass.name)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(context : Context) = ViewModelFactory(InjectionModule.provideRepository(context))
    }
}