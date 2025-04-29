package com.elevii.comidanamedida.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elevii.comidanamedida.data.local.converter.Converter
import com.elevii.comidanamedida.data.local.dao.CookedFoodMeasurementDao
import com.elevii.comidanamedida.data.local.dao.FoodDao
import com.elevii.comidanamedida.data.local.entity.CookedFoodMeasurementEntity
import com.elevii.comidanamedida.data.local.entity.FoodEntity

@Database(
    entities = [
        FoodEntity::class,
        CookedFoodMeasurementEntity::class
    ],
    version = 1,
    exportSchema = true
)

@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun cookedFoodMeasurementDao(): CookedFoodMeasurementDao

}