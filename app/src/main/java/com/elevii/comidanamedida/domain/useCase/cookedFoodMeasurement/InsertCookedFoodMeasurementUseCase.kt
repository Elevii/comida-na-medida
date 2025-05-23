package com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository
import java.time.LocalDateTime
import java.util.UUID

class InsertCookedFoodMeasurementUseCase(private val repository: CookedFoodMeasurementRepository) {
    suspend operator fun invoke(
        weightRaw: Double,
        weightCooked: Double,
        uuidFood: String
    ) {
        repository.insert(
            CookedFoodMeasurement(
                uuid =  UUID.randomUUID().toString() ,
                weightRaw = weightRaw,
                weightCooked = weightCooked,
                calculationDate = LocalDateTime.now(),
                uuidFood = uuidFood
            )
        )
    }
}
