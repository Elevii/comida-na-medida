package com.elevii.comidanamedida.domain.repository

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement

interface CookedFoodMeasurementRepository {

    suspend fun getAll(): List<CookedFoodMeasurement>

    suspend fun getByUuid(uuid: String): CookedFoodMeasurement

    suspend fun insert(cookedFoodMeasurement: CookedFoodMeasurement)

    suspend fun remove(cookedFoodMeasurement: CookedFoodMeasurement)

    suspend fun removeAll()
}
