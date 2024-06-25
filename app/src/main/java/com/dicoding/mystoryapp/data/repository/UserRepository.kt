package com.dicoding.mystoryapp.data.repository

import com.dicoding.mystoryapp.data.api.ApiService
import com.dicoding.mystoryapp.data.preference.UserPreference
import com.dicoding.mystoryapp.data.response.DetailStoryResponse
import com.dicoding.mystoryapp.data.response.StoryResponse

class UserRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {
    suspend fun getStories(): StoryResponse {
        return apiService.getStories()
    }

    fun getSession() = userPreference.getSession()

    suspend fun getStoryDetail(storyId: String): DetailStoryResponse {
        return apiService.getStoryDetail(storyId)
    }

    suspend fun signout() = userPreference.logout()

    companion object{
        @Volatile
        private var instance: UserRepository?= null
        fun getInstance(apiService: ApiService, userPreference: UserPreference) = UserRepository(apiService, userPreference)
    }
}