package com.elevii.comidanamedida.data.remote.api

import com.elevii.comidanamedida.data.remote.model.FoodDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface FoodApiService {
    @GET("food")
    suspend fun getFoods(): List<FoodDto>
}