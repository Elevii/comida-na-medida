package com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository

class RemoveCookedFoodMeasurementUseCase(private val repository: CookedFoodMeasurementRepository) {
    suspend operator fun invoke(cookedFoodMeasurement: CookedFoodMeasurement) {
        repository.remove(cookedFoodMeasurement = cookedFoodMeasurement)
    }
}
