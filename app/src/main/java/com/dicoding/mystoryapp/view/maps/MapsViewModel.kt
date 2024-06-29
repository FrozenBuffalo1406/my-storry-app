package com.dicoding.mystoryapp.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.repository.UserRepository
import com.dicoding.mystoryapp.data.response.StoryResponse
import kotlinx.coroutines.launch

class MapsViewModel (private val repo: UserRepository): ViewModel(){

    private val _stories = MutableLiveData<StoryResponse>()
    val stories: LiveData<StoryResponse> = _stories

    fun getStoriesLocation(){
        viewModelScope.launch {
            val response=  repo.getStoriesLocation()
            _stories.postValue(response)
        }
    }
}