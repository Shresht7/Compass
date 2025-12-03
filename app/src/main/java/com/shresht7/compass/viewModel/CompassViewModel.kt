package com.shresht7.compass.viewModel

import android.Manifest
import android.app.Application
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shresht7.compass.sensor.CompassSensor
import com.shresht7.compass.sensor.LocationManager
import com.shresht7.compass.state.CompassState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CompassViewModel(application: Application) : AndroidViewModel(application) {
    private val compassSensor = CompassSensor(application)
    private val locationManager = LocationManager(application)

    private val _compassState = MutableStateFlow(CompassState())
    val compassState: StateFlow<CompassState> = _compassState.asStateFlow()

    init {
        startCompass()
    }

    private fun startCompass() {
        compassSensor.getCompassFlow().onEach { azimuth ->
            _compassState.value = _compassState.value.copy(azimuth = azimuth)
        }.launchIn(viewModelScope)
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun startLocationUpdates() {
        locationManager.getLocationFlow().onEach { location ->
            _compassState.value = _compassState.value.copy(location = location)
        }.launchIn(viewModelScope)
    }
}