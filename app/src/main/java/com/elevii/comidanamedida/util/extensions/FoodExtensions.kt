package com.elevii.comidanamedida.util.extensions

import com.elevii.comidanamedida.domain.model.Food

fun Food.calculateRawWeight(weightCooked: Double): Double =
    weightCooked / cookingIndex