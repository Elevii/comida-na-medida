package com.elevii.comidanamedida.ui.home.events

sealed class SaveMeasurementEvent {
    object Success : SaveMeasurementEvent()
    data class Error(val message: String) : SaveMeasurementEvent()
}
