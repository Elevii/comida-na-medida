package com.elevii.comidanamedida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elevii.comidanamedida.data.exceptions.ErrorHandler
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.useCase.food.GetAllFoodsUseCase
import com.elevii.comidanamedida.domain.useCase.food.RefreshFoodsUseCase
import com.elevii.comidanamedida.util.Resource
import com.elevii.comidanamedida.util.extensions.calculateRawWeight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val refreshFoodsUseCase: RefreshFoodsUseCase
) : ViewModel() {

    private val _foods = MutableStateFlow<Resource<List<Food>>>(Resource.Loading())
    val foods: StateFlow<Resource<List<Food>>> = _foods

    private val _measurement = MutableStateFlow<CookedFoodMeasurement?>(null)
    val measurement: StateFlow<CookedFoodMeasurement?> = _measurement

    init {
        observeDb()
        refreshFoods()
    }

    private fun observeDb() {
        viewModelScope.launch {
            try {
                getAllFoodsUseCase().collect { foods ->
                    _foods.value = Resource.Success(foods)
                }
            } catch (e: Exception) {
                _foods.value = Resource.Error(e.message.toString())
            }
        }
    }

    private fun refreshFoods() = viewModelScope.launch {
        try {
            refreshFoodsUseCase()
        } catch (e: Throwable) {
            val connectionError = ErrorHandler.handleError(e)
            _foods.value = Resource.Error(ErrorHandler.customMessageError(connectionError))
        }
    }

    fun calculateMeasurement(cookedWeight: Double, food: Food) {
        val raw = food.calculateRawWeight(cookedWeight)

        _measurement.value = CookedFoodMeasurement(
            uuid = UUID.randomUUID().toString(),
            weightRaw = raw,
            weightCooked = cookedWeight,
            calculationDate = LocalDateTime.now(),
            uuidFood = food.uuid
        )
    }

    fun clearMeasurement() {
        _measurement.value = null
    }
}
