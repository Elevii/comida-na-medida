package com.elevii.comidanamedida.domain.useCase.food

import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetAllFoodsUseCase(private val repository: FoodRepository) {
    operator fun invoke(): Flow<List<Food>> {
        return repository.getAll()
    }
}