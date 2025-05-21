package com.elevii.comidanamedida.domain.useCase.food

import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.repository.FoodRepository

class InsertFoodUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(uuid: String, name: String, cookingIndex: Double ) {
        repository.insert(Food(
                uuid = uuid,
                name = name,
                cookingIndex = cookingIndex
        ))
    }
}