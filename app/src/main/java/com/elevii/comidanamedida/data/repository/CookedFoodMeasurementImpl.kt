package com.elevii.comidanamedida.data.repository

import com.elevii.comidanamedida.data.local.dao.CookedFoodMeasurementDao
import com.elevii.comidanamedida.data.local.entity.toDomain
import com.elevii.comidanamedida.data.local.entity.toEntity
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository
import javax.inject.Inject

class CookedFoodMeasurementImpl @Inject constructor(private val dao: CookedFoodMeasurementDao) :
    CookedFoodMeasurementRepository {

    override suspend fun getAll(): List<CookedFoodMeasurement> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun getByUuid(uuid: String): CookedFoodMeasurement {
        return dao.getByUuid(uuid).toDomain()
    }

    override suspend fun insert(cookedFoodMeasurement: CookedFoodMeasurement) {
        dao.insert(cookedFoodMeasurement.toEntity())
    }

    override suspend fun remove(cookedFoodMeasurement: CookedFoodMeasurement) {
        dao.remove(cookedFoodMeasurement.toEntity())
    }

    override suspend fun removeAll() {
        dao.removeAll()
    }
}
