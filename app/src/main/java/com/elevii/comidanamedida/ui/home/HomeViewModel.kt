package com.elevii.comidanamedida.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.useCase.food.GetAllFoodsUseCase
import com.elevii.comidanamedida.domain.useCase.food.RefreshFoodsUseCase
import com.elevii.comidanamedida.util.extensions.calculateRawWeight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val refreshFoodsUseCase: RefreshFoodsUseCase
) : ViewModel() {

    val foods: StateFlow<List<Food>> = getAllFoodsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _measurement = MutableStateFlow<CookedFoodMeasurement?>(null)
    val measurement: StateFlow<CookedFoodMeasurement?> = _measurement

    fun refreshFoods() {
        viewModelScope.launch {
            refreshFoodsUseCase()
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
}