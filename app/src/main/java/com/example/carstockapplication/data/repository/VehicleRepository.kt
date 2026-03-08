package com.example.carstockapplication.data.repository

import com.example.carstockapplication.data.api.VehicleApi
import com.example.carstockapplication.domain.model.Pageable
import com.example.carstockapplication.domain.model.Vehicle
import javax.inject.Inject

class VehicleRepository @Inject constructor(private val api: VehicleApi) {
    suspend fun getVehicles(): Pageable<Vehicle> {
        return api.getVehicles()
    }

    suspend fun createVehicle(vehicle: Vehicle) {
        api.createVehicle(vehicle)
    }
}