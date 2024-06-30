package com.dicoding.mystoryapp.view.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mystoryapp.data.api.ApiConfig
import com.dicoding.mystoryapp.repository.UserRepository
import com.dicoding.mystoryapp.data.response.FileUploadResponse
import com.dicoding.mystoryapp.data.response.ListStoryItem
import com.dicoding.mystoryapp.data.response.StoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UploadViewModel(private val repo: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val storiess: LiveData<PagingData<ListStoryItem>> =
        repo.getStories().cachedIn(viewModelScope)

    private val _stories = MutableLiveData<StoryResponse>()
    val stories: LiveData<StoryResponse> = _stories

    val uploadResult = MutableLiveData<FileUploadResponse>()

    fun uploadImage(multipartBody: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repo.uploadImage(multipartBody, description)
                uploadResult.postValue(response)
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("UploadViewModel", "Error uploading image: ${e.message}", e)
                _isLoading.value = false
                // Handle error case
            }
        }
    }

    fun fetchStories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repo.storyRepo()
                _stories.postValue(response)
                _isLoading.value = false
                Log.d("UploadViewModel", "Stories fetched successfully")
            } catch (e: HttpException) {
                Log.e("UploadViewModel", "HTTP Exception: ${e.message}", e)
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("UploadViewModel", "Exception: ${e.message}", e)
                _isLoading.value = false
            }
        }
    }
}
