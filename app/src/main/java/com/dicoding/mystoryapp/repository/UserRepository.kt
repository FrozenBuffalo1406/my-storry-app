package com.dicoding.mystoryapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.mystoryapp.data.api.ApiService
import com.dicoding.mystoryapp.data.paging.StoryPagingSource
import com.dicoding.mystoryapp.data.preference.UserPreference
import com.dicoding.mystoryapp.data.response.DetailStoryResponse
import com.dicoding.mystoryapp.data.response.FileUploadResponse
import com.dicoding.mystoryapp.data.response.ListStoryItem
import com.dicoding.mystoryapp.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    suspend fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody
    ): FileUploadResponse{
        return apiService.uploadImage(file, description)
    }

    suspend fun storyRepo(): StoryResponse {
        return apiService.getStories()
    }

    fun getSession() = userPreference.getSession()

    suspend fun getStoryDetail(storyId: String): DetailStoryResponse {
        return apiService.getStoryDetail(storyId)
    }

    suspend fun getStoriesLocation(): StoryResponse {
        return apiService.getStoriesLocation(location = 1)
    }

    suspend fun signout() = userPreference.logout()

    companion object{
        @JvmStatic
        fun getInstance(apiService: ApiService, userPreference: UserPreference) : UserRepository {
           return UserRepository(apiService, userPreference)
        }
    }
}