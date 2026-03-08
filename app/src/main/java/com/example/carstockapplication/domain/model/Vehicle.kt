package com.example.carstockapplication.domain.model

data class Vehicle (
    val id: String,
    val name: String,
    val versionId: String,
    val versionName: String,
    val modelId: String,
    val modelName: String,
    val brandId: String,
    val brandName: String,
    val modelYear: Int,
    val manufacturerYear: Int,
    val chassis: String,
    val licensePlate: String,
    val color: String,
    val mileage: Int,
    val salePrice: Double,
    val costPrice: Double,
    val fuelType: String,
    val status: String,
    val entryDate: String,
    val optionals: List<VehicleOptional> = emptyList()
)