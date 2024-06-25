package com.dicoding.mystoryapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.model.User
import com.dicoding.mystoryapp.data.repository.UserRepository
import com.dicoding.mystoryapp.data.response.StoryResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repo: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _stories = MutableLiveData<StoryResponse>()
    val stories: LiveData<StoryResponse> = _stories

    fun getStories(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val storyResponse = repo.getStories()
                _stories.value = storyResponse
            } catch (e: Exception) {
                _stories.value = StoryResponse(error = true, message = e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getSession(): LiveData<User> {
        return repo.getSession().asLiveData()
    }

    fun signout() {
        viewModelScope.launch {
            repo.signout()
        }
    }

}