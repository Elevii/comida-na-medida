package com.elevii.comidanamedida.ui.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elevii.comidanamedida.domain.model.CookedFoodMeasurement
import com.elevii.comidanamedida.domain.model.Food
import com.elevii.comidanamedida.domain.useCase.cookedFoodMeasurement.GetAllCookedFoodMeasurementsUseCase
import com.elevii.comidanamedida.domain.useCase.food.GetAllFoodsUseCase
import com.elevii.comidanamedida.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricViewModel @Inject constructor(
    private val getAllCookedFoodMeasurementsUseCase: GetAllCookedFoodMeasurementsUseCase,
    private val getAllFoodsUseCase: GetAllFoodsUseCase
) : ViewModel() {

    private val _cookedFoodMeasurements =
        MutableStateFlow<Resource<List<CookedFoodMeasurement>>>(Resource.Loading())
    val cookedFoodMeasurement: StateFlow<Resource<List<CookedFoodMeasurement>>> =
        _cookedFoodMeasurements

    private val _foods = MutableStateFlow<Resource<List<Food>>>(Resource.Loading())
    val foods: StateFlow<Resource<List<Food>>> = _foods


    init {
        observeMeasurementsDb()
        observeFoodsDb()
    }

    private fun observeMeasurementsDb() {
        viewModelScope.launch {
            try {
                getAllCookedFoodMeasurementsUseCase().collect { cookedFoodMeasurements ->
                    _cookedFoodMeasurements.value = Resource.Success(cookedFoodMeasurements)
                }
            } catch (e: Exception) {
                _cookedFoodMeasurements.value = Resource.Error(e.message.toString())
            }
        }
    }

    private fun observeFoodsDb() {
        viewModelScope.launch {
            try {
                getAllFoodsUseCase().collect { food ->
                    _foods.value = Resource.Success(food)
                }
            } catch (e: Exception) {
                _foods.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun deleteMeasurement(cookedFoodMeasurement: CookedFoodMeasurement) {

    }
}
