package com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository

class GetByUuidCookedFoodMeasurementUseCase(private val repository: CookedFoodMeasurementRepository) {
    operator fun invoke(uuid: String): CookedFoodMeasurement {
        return repository.getByUuid(uuid)
    }
}