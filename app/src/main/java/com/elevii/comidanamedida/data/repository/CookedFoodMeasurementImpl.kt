package com.elevii.comidanamedida.data.repository

import com.elevii.comidanamedida.data.local.dao.CookedFoodMeasurementDao
import com.elevii.comidanamedida.data.local.entity.toDomain
import com.elevii.comidanamedida.data.local.entity.toEntity
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CookedFoodMeasurementImpl @Inject constructor(private val dao: CookedFoodMeasurementDao) :
    CookedFoodMeasurementRepository {

    override fun getAll(): Flow<List<CookedFoodMeasurement>> {
        return dao.getAll().map { list ->
            list.map { it.toDomain() }
        }
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
