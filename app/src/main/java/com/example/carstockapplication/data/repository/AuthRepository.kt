package com.example.carstockapplication.data.repository

import com.example.carstockapplication.data.api.AuthApi
import com.example.carstockapplication.domain.model.LoginRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: AuthApi) {
    suspend fun login(email: String, password: String) {
        api.login(LoginRequest(email, password))
    }
}