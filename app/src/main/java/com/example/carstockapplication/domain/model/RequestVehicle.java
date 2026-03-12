package com.example.carstockapplication.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

data class RequestVehicle(
        val name: String,
        val versionId: UUID,
        val chassis: String,
        val licensePlate: String,
        val modelYear: Int,
        val manufacturerYear: Int,
        val color: String,
        val mileage: Int,
        val salePrice: BigDecimal,
        val costPrice: BigDecimal,
        val status: String,
        val entryDate: LocalDateTime,
        val optionalIds: List<UUID>
)
