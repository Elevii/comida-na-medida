package com.elevii.comidanamedida.domain.repository

import com.elevii.comidanamedida.domain.model.Food

interface FoodRepository {

    fun insert(food: Food)

    fun getAll(): List<Food>

    fun getByUuid(uuid: String): Food

    suspend fun fetchAndSaveFoods()
}