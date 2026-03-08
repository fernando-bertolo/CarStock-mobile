package com.example.carstockapplication.data.api

import com.example.carstockapplication.domain.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest)
}