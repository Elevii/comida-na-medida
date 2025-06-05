package com.elevii.comidanamedida.ui.historic.events

sealed class DeleteMeasurementEvent {
    object Success : DeleteMeasurementEvent()
    data class Error(val message: String) : DeleteMeasurementEvent()
}
