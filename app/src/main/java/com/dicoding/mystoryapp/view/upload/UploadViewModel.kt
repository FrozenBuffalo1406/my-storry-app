package com.dicoding.mystoryapp.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.api.ApiConfig
import com.dicoding.mystoryapp.repository.UserRepository
import com.dicoding.mystoryapp.data.response.FileUploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel (private val repo: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun saveImage(token: String, multipartBody: MultipartBody.Part, description: RequestBody) : FileUploadResponse {
        return withContext(Dispatchers.IO) {
            try {
                val apiService = ApiConfig.getApiService(token)
                val response = apiService.uploadImage(multipartBody, description)
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun getStories() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
            } catch (_: Exception) {
            } finally {
                _isLoading.value = true
            }
        }
    }

}