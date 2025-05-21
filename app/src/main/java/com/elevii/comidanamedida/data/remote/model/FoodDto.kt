package com.elevii.comidanamedida.data.remote.model

import com.elevii.comidanamedida.domain.model.Food

data class FoodDto(
    val uuid: String,
    val name: String,
    val cookingIndex: Double
)

fun FoodDto.toDomain(): Food {
    return Food(
        uuid = this.uuid,
        name = this.name,
        cookingIndex = this.cookingIndex
    )
}
