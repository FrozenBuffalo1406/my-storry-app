package com.dicoding.mystoryapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.mystoryapp.data.api.ApiService
import com.dicoding.mystoryapp.data.db.StoryDatabase
import com.dicoding.mystoryapp.data.mediator.StoryRemoteMediator
import com.dicoding.mystoryapp.data.preference.UserPreference
import com.dicoding.mystoryapp.data.response.DetailStoryResponse
import com.dicoding.mystoryapp.data.response.ListStoryItem
import com.dicoding.mystoryapp.data.response.StoryResponse


class UserRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val storyDatabase: StoryDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).liveData
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
        fun getInstance(apiService: ApiService, userPreference: UserPreference, storyDatabase: StoryDatabase) = UserRepository(apiService, userPreference, storyDatabase)
    }
}