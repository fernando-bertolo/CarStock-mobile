package com.example.carstockapplication.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carstockapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()
    val loginSuccess = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                repository.login(email, password)
                loginSuccess.value = true
            } catch (e: Exception) {
                error.value = "E-mail ou senha inválidos"
            } finally {
                isLoading.value = false
            }
        }
    }
}