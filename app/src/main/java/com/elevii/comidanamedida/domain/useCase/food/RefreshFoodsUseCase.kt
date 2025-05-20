package com.elevii.comidanamedida.domain.useCase.food

import com.elevii.comidanamedida.domain.repository.FoodRepository

class RefreshFoodsUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke() {
        repository.refreshFoods()
    }
}