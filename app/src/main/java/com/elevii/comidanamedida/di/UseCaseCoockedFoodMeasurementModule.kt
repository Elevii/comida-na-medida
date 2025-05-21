package com.elevii.comidanamedida.di

import com.elevii.comidanamedida.domain.repository.CookedFoodMeasurementRepository
import com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement.GetAllCookedFoodMeasurementsUseCase
import com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement.GetByUuidCookedFoodMeasurementUseCase
import com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement.InsertCookedFoodMeasurementUseCase
import com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement.RemoveAllCookedFoodMeasurementUseCase
import com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement.RemoveCookedFoodMeasurementUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseCoockedFoodMeasurementModule {

    @Provides
    fun provideGetAllCookedFoodMeasurementsUseCase(
        repository: CookedFoodMeasurementRepository
    ): GetAllCookedFoodMeasurementsUseCase = GetAllCookedFoodMeasurementsUseCase(repository)

    @Provides
    fun provideGetByUuidCookedFoodMeasurementsUseCase(
        repository: CookedFoodMeasurementRepository
    ): GetByUuidCookedFoodMeasurementUseCase = GetByUuidCookedFoodMeasurementUseCase(repository)

    @Provides
    fun provideInsertCookedFoodMeasurementUseCase(
        repository: CookedFoodMeasurementRepository
    ): InsertCookedFoodMeasurementUseCase = InsertCookedFoodMeasurementUseCase(repository)

    @Provides
    fun provideRemoveAllCookedFoodMeasurementsUseCase(
        repository: CookedFoodMeasurementRepository
    ): RemoveAllCookedFoodMeasurementUseCase = RemoveAllCookedFoodMeasurementUseCase(repository)

    @Provides
    fun provideRemoveCookedFoodMeasurementUseCase(
        repository: CookedFoodMeasurementRepository
    ): RemoveCookedFoodMeasurementUseCase = RemoveCookedFoodMeasurementUseCase(repository)
}
