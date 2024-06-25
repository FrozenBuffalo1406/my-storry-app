package com.dicoding.mystoryapp.repository

import com.dicoding.mystoryapp.data.api.AuthService
import com.dicoding.mystoryapp.data.model.User
import com.dicoding.mystoryapp.data.preference.UserPreference
import com.dicoding.mystoryapp.data.response.SigninResponse
import com.dicoding.mystoryapp.data.response.SignupResponse
import retrofit2.HttpException

class AuthRepository(private val authService: AuthService, private val userPreference: UserPreference) {

    suspend fun signup(name: String, email: String, password: String): SignupResponse {
        return authService.signup(name, email, password)
    }

    suspend fun signin(email: String, password: String): SigninResponse {
        return try {
            val response = authService.signin(email, password)
            if (!response.error!!) {
                response.loginResult?.token?.let { token ->
                    userPreference.saveSession(User(email, token, true))
                }
            }
            response
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> SigninResponse(error = true, message = "Invalid email or password")
                else -> SigninResponse(error = true, message = "Failed to log in: ${e.message()}")
            }
        } catch (e: Exception) {
            SigninResponse(error = true, message = "Network error: ${e.message}")
        }
    }

    suspend fun saveSession(userModel: User) {
        userPreference.saveSession(userModel)
    }

    companion object {
        fun getInstance(authService: AuthService, userPreference: UserPreference) = AuthRepository(authService, userPreference)
    }
}
