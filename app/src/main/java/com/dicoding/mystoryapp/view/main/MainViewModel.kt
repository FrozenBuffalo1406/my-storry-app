package com.dicoding.mystoryapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mystoryapp.data.model.User
import com.dicoding.mystoryapp.repository.UserRepository
import com.dicoding.mystoryapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repo: UserRepository) : ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    val story: LiveData<PagingData<ListStoryItem>> =
        repo.getStories().cachedIn(viewModelScope)

    fun getSession(): LiveData<User> {
        return repo.getSession().asLiveData()
    }

    fun signout() {
        viewModelScope.launch {
            repo.signout()
        }
    }

}