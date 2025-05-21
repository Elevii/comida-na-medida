package com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository

class GetAllCookedFoodMeasurementsUseCase(private val repository: CookedFoodMeasurementRepository) {
    operator fun invoke(): List<CookedFoodMeasurement> {
        return repository.getAll()
    }
}
