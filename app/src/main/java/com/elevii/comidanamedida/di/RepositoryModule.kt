package com.elevii.comidanamedida.di

import com.elevii.comidanamedida.data.repository.CookedFoodMeasurementImpl
import com.elevii.comidanamedida.data.repository.FoodRepositoryImpl
import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository
import com.elevii.comidanamedida.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFoodRepository(
        impl: FoodRepositoryImpl
    ): FoodRepository

    @Binds
    abstract fun bindCookedFoodMeasurementRepository(
        impl: CookedFoodMeasurementImpl
    ): CookedFoodMeasurementRepository
}