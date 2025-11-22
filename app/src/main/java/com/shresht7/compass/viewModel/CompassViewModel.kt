package com.shresht7.compass.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shresht7.compass.sensor.CompassSensor
import com.shresht7.compass.state.CompassState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CompassViewModel(application: Application): AndroidViewModel(application) {
    private val compassSensor = CompassSensor(application)

    private val _compassState = MutableStateFlow(CompassState())
    val compassState: StateFlow<CompassState> = _compassState.asStateFlow()

    init {
        startCompass()
    }

    private fun startCompass() {
        viewModelScope.launch {
            compassSensor.getCompassFlow().collect { azimuth ->
                _compassState.value = _compassState.value.copy(azimuth = azimuth)
            }
        }
    }
}