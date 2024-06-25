package com.dicoding.mystoryapp.view.singup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.repository.AuthRepository
import com.dicoding.mystoryapp.data.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupViewModel(private val repo: AuthRepository) : ViewModel() {
    private val _signUpStatus = MutableLiveData<SignUpStatus>()
    val signUpStatus : LiveData<SignUpStatus>
        get() = _signUpStatus


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    sealed class SignUpStatus {
        data object Loading : SignUpStatus()
        data class Success(val message: String) : SignUpStatus()
        data class Error(val message: String): SignUpStatus()
    }

    fun signup(name: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            _signUpStatus.value = SignUpStatus.Loading
            try {
                val response = repo.signup(name, email, password)
                if (!response.error) {
                    _signUpStatus.value = SignUpStatus.Success(response.message)
                } else {
                    _signUpStatus.value = SignUpStatus.Error(response.message)
                }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _signUpStatus.value = errorMessage?.let { SignUpStatus.Error(it) }
            } catch (e: Exception) {
                _signUpStatus.value = SignUpStatus.Error(e.message ?: "Something went wrong during registration")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

