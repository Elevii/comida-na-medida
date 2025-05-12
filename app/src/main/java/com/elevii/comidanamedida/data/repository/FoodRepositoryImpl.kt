package com.elevii.comidanamedida.data.repository

import com.elevii.comidanamedida.data.local.dao.FoodDao
import com.elevii.comidanamedida.data.local.entity.toDomain
import com.elevii.comidanamedida.data.local.entity.toEntity
import com.elevii.comidanamedida.data.remote.api.FoodApiService
import com.elevii.comidanamedida.data.remote.model.toDomain
import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val dao: FoodDao,
    private val api: FoodApiService
) : FoodRepository {

    override fun insert(food: Food) {
        dao.insert(food.toEntity())
    }

    override fun getAll(): List<Food> {
        return dao.getAll().map { it.toDomain() }
    }

    override fun getByUuid(uuid: String): Food {
        return dao.getByUuid(uuid).toDomain()
    }

    override suspend fun fetchAndSaveFoods() {
        try {
            val foodsDto = api.getFoods()
            val foodsDomain = foodsDto.map { it.toDomain() }

            foodsDomain.forEach { food ->
                dao.insert(food.toEntity())
            }
        } catch (e: Exception) {
            //TODO: implementar erro
        }
    }
}