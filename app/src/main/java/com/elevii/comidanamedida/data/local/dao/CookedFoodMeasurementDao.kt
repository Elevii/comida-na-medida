package com.elevii.comidanamedida.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elevii.comidanamedida.data.local.entity.CookedFoodMeasurementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CookedFoodMeasurementDao {

    @Query("SELECT * FROM CookedFoodMeasurementEntity ORDER BY calculationDate")
    fun getAll(): Flow<List<CookedFoodMeasurementEntity>>

    @Query("SELECT * FROM CookedFoodMeasurementEntity WHERE uuid = :uuid")
    suspend fun getByUuid(uuid: String): CookedFoodMeasurementEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg cookedFoodMeasurementFood: CookedFoodMeasurementEntity)

    @Delete
    suspend fun remove(cookedFoodMeasurementFood: CookedFoodMeasurementEntity)

    @Query("DELETE FROM CookedFoodMeasurementEntity")
    suspend fun removeAll()
}
