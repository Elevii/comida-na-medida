package com.elevii.comidanamedida.domain.repository

import com.elevii.comidanamedida.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    suspend fun insert(food: Food)

    fun getAll(): Flow<List<Food>>

    fun getByUuid(uuid: String): Food

    suspend fun refreshFoods()
}