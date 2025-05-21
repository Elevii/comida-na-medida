package com.elevii.comidanamedida.domain.model

import java.time.LocalDateTime

class CookedFoodMeasurement(
    val uuid: String,
    var weightRaw: Double,
    val weightCooked: Double,
    val calculationDate: LocalDateTime,
    val uuidFood: String
)
