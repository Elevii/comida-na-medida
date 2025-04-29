package com.elevii.comidanamedida.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elevii.comidanamedida.domain.model.Food

@Entity
data class FoodEntity (
    @PrimaryKey
    val uuid: String,
    val name: String,
    val cookingIndex: Double
)

fun FoodEntity.toDomain() = Food(
    uuid = uuid,
    name = name,
    cookingIndex = cookingIndex
)

fun Food.toEntity() = FoodEntity(
    uuid = uuid,
    name = name,
    cookingIndex = cookingIndex
)