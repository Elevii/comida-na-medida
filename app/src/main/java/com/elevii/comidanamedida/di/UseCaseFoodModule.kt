package com.elevii.comidanamedida.di

import com.elevii.comidanamedida.domain.repository.FoodRepository
import com.elevii.comidanamedida.domain.useCase.food.GetAllFoodsUseCase
import com.elevii.comidanamedida.domain.useCase.food.GetFoodByUuidUseCase
import com.elevii.comidanamedida.domain.useCase.food.InsertFoodUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseFoodModule {

    @Provides
    fun provideInsertFoodUseCase(
        repository: FoodRepository
    ): InsertFoodUseCase = InsertFoodUseCase(repository)

    @Provides
    fun provideGetAllFoodsUseCase(
        repository: FoodRepository
    ): GetAllFoodsUseCase = GetAllFoodsUseCase(repository)

    @Provides
    fun provideGetFoodByIdUseCase(
        repository: FoodRepository
    ): GetFoodByUuidUseCase = GetFoodByUuidUseCase(repository)

}