package com.elevii.comidanamedida.di

import android.content.Context
import androidx.room.Room
import com.elevii.comidanamedida.data.local.AppDatabase
import com.elevii.comidanamedida.data.local.dao.CookedFoodMeasurementDao
import com.elevii.comidanamedida.data.local.dao.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "comidanamedida.db"
        ).build()
    }

    @Provides
    fun provideFoodDao(db: AppDatabase): FoodDao = db.foodDao()

    @Provides
    fun provideCookedFoodMeasurementDao(db: AppDatabase): CookedFoodMeasurementDao = db.cookedFoodMeasurementDao()
}