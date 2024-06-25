package com.dicoding.mystoryapp.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.repository.UserRepository
import com.dicoding.mystoryapp.data.response.DetailStoryResponse
import kotlinx.coroutines.launch

class DetailViewModel (private val repo: UserRepository): ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _story = MutableLiveData<DetailStoryResponse>()
    val story: LiveData<DetailStoryResponse> = _story

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getDetail(storyId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repo.getStoryDetail(storyId)
                _story.value = response
            } catch (e: Exception) {
                _error.value = DetailStoryResponse(error = true, message = e.message).toString()
            } finally {
                _isLoading.value = false
            }
        }
    }
}