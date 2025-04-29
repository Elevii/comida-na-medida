package com.elevii.comidanamedida.domain.useCase.food

import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.repository.FoodRepository

class GetAllFoodsUseCase(private val repository: FoodRepository) {
    operator fun invoke(): List<Food> {
        return repository.getAll()
    }
}