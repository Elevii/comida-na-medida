package com.elevii.comidanamedida.domain.useCase.food

import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.repository.FoodRepository

class GetFoodByUuidUseCase(private val repository: FoodRepository) {
    operator fun invoke(uuid:String): Food {
        return repository.getByUuid(uuid)
    }
}