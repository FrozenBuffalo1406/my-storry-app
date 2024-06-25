package com.dicoding.mystoryapp.view.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.model.User
import com.dicoding.mystoryapp.repository.AuthRepository
import com.dicoding.mystoryapp.data.response.SigninResponse
import kotlinx.coroutines.launch

class SigninViewModel(private val repo : AuthRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<SigninResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _loginStatus = MutableLiveData<SignInStatus>()
    val loginStatus: LiveData<SignInStatus>
        get() = _loginStatus

    sealed class SignInStatus {
        data class NetworkError(val message: String) : SignInStatus()
        data class Success(val message: String) : SignInStatus()
        data class Error(val message: String): SignInStatus()
    }

    fun signin(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repo.signin(email, password)
                _loginResult.value = response
                if (response.error == false) {
                    val token = response.loginResult?.token
                    if (token != null) {
                        val userModel = User(email, token, true)
                        saveSession(userModel)
                        _loginStatus.value = SignInStatus.Success(response.message ?: "Logged in successfully")
                    } else {
                        _loginStatus.value = SignInStatus.Error("Token is null")
                    }
                } else {
                    _loginStatus.value = SignInStatus.Error(response.message ?: "Failed to log in")
                }
            } catch (e: Exception) {
                val networkErrorMessage = "Network error occurred"
                _loginStatus.value = SignInStatus.NetworkError(e.message ?: networkErrorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }
    private fun saveSession(user: User) {
        viewModelScope.launch {
            repo.saveSession(user)
        }
    }
}