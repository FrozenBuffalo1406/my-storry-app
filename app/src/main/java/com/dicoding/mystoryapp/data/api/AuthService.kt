package com.dicoding.mystoryapp.data.api

import com.dicoding.mystoryapp.data.response.SigninResponse
import com.dicoding.mystoryapp.data.response.SignupResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("register")
    suspend fun signup (
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : SignupResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun signin (
        @Field("email") email: String,
        @Field("password") password: String
    ): SigninResponse
}