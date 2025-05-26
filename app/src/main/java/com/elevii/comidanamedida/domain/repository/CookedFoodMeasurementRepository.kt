package com.elevii.comidanamedida.domain.repository

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import kotlinx.coroutines.flow.Flow

interface CookedFoodMeasurementRepository {

    fun getAll(): Flow<List<CookedFoodMeasurement>>

    suspend fun getByUuid(uuid: String): CookedFoodMeasurement

    suspend fun insert(cookedFoodMeasurement: CookedFoodMeasurement)

    suspend fun remove(cookedFoodMeasurement: CookedFoodMeasurement)

    suspend fun removeAll()
}
