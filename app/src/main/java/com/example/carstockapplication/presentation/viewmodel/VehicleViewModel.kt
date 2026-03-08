package com.example.carstockapplication.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carstockapplication.data.repository.VehicleRepository
import com.example.carstockapplication.domain.model.Pageable
import com.example.carstockapplication.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {
    val vehicles = MutableLiveData<Pageable<Vehicle>>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun loadVehicles() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                vehicles.value = repository.getVehicles()
                error.value = null
            } catch(e: Exception) {
                error.value = "Erro ao carregar veículos: ${e.message}"
            } finally {
                isLoading.value = false
            }

        }
    }
}