package com.example.carstockapplication.data.api

import com.example.carstockapplication.domain.model.Pageable
import com.example.carstockapplication.domain.model.Vehicle
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VehicleApi {
    @GET("vehicles")
    suspend fun getVehicles(): Pageable<Vehicle>

    @POST("vehicles")
    suspend fun createVehicle(@Body vehicle: Vehicle)
}