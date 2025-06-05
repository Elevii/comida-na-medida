package com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository
import kotlinx.coroutines.flow.Flow

class GetAllCookedFoodMeasurementsUseCase(private val repository: CookedFoodMeasurementRepository) {
    operator fun invoke(): Flow<List<CookedFoodMeasurement>> {
        return repository.getAll()
    }
}
