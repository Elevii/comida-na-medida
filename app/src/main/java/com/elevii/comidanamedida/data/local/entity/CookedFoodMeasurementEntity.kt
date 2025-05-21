package com.elevii.comidanamedida.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import java.time.LocalDateTime

@Entity
class CookedFoodMeasurementEntity(
    @PrimaryKey
    val uuid: String,
    var weightRaw: Double,
    val weightBaked: Double,
    val calculationDate: LocalDateTime,
    val uuidFood: String
)

fun CookedFoodMeasurementEntity.toDomain() = CookedFoodMeasurement(
    uuid = uuid,
    weightRaw = weightRaw,
    weightCooked = weightBaked,
    calculationDate = calculationDate,
    uuidFood = uuidFood
)

fun CookedFoodMeasurement.toEntity() = CookedFoodMeasurementEntity(
    uuid = uuid,
    weightRaw = weightRaw,
    weightBaked = weightCooked,
    calculationDate = calculationDate,
    uuidFood = uuidFood
)
