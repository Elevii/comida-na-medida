package com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement

import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository

class RemoveAllCookedFoodMeasurementUseCase(private val repository: CookedFoodMeasurementRepository) {
    operator fun invoke() {
        repository.removeAll()
    }
}
