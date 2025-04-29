package com.elevii.comidanamedida.domain.repository

import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement

interface CookedFoodMeasurementRepository {

    fun getAll(): List<CookedFoodMeasurement>

    fun getByUuid(uuid: String): CookedFoodMeasurement

    fun insert(cookedFoodMeasurement: CookedFoodMeasurement)

    fun remove(cookedFoodMeasurement: CookedFoodMeasurement)

    fun removeAll()
}