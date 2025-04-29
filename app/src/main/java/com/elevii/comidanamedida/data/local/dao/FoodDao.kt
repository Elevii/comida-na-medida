package com.elevii.comidanamedida.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elevii.comidanamedida.data.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg food: FoodEntity)

    @Query("SELECT * FROM FoodEntity")
    fun getAll() : List<FoodEntity>

    @Query("SELECT * FROM FoodEntity WHERE uuid = :uuid")
    fun getByUuid(uuid: String): FoodEntity
}