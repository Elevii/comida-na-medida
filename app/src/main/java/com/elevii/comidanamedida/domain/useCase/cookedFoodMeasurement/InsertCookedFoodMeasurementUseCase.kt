package com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository
import java.time.LocalDateTime
import java.util.UUID

class InsertCookedFoodMeasurementUseCase(private val repository: CookedFoodMeasurementRepository) {
    operator fun invoke(
        uuid: String?,
        weightRaw: Double,
        weightCooked: Double,
        uuidFood: String
    ){
        repository.insert(CookedFoodMeasurement(
            uuid = if (uuid.isNullOrBlank()) UUID.randomUUID().toString() else uuid,
            weightRaw = weightRaw,
            weightCooked = weightCooked,
            calculationDate = LocalDateTime.now(),
            uuidFood = uuidFood,
        ))
    }
}