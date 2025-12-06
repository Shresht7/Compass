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

import com.shresht7.compass.settings.AppSettingsManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.catch

/**
 * The ViewModel for the compass screen.
 *
 * This class is responsible for managing the state of the compass screen, including the
 * compass data, location information, and sensor delay settings. It interacts with the
 * `CompassSensor`, `LocationManager`, and `AppSettingsManager` to keep the state up-to-date.
 *
 * @param application The application instance.
 * @param appSettingsManager The manager for application settings.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CompassViewModel(application: Application, appSettingsManager: AppSettingsManager) : AndroidViewModel(application) {
    private val compassSensor = CompassSensor(application)
    private val locationManager = LocationManager(application)

    private val _compassState = MutableStateFlow(CompassState())
    val compassState: StateFlow<CompassState> = _compassState.asStateFlow()

    init {
        appSettingsManager.sensorDelay
            .flatMapLatest { delay ->
                compassSensor.getCompassFlow(delay)
            }
            .onEach { compassData ->
                _compassState.value = _compassState.value.copy(
                    azimuth = compassData.azimuth,
                    magneticField = compassData.magneticField
                )
            }
            .catch { e ->
                // Handle the exception, e.g. by logging it or showing an error message
                e.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun startLocationUpdates() {
        locationManager.getLocationFlow().onEach { location ->
            _compassState.value = _compassState.value.copy(location = location)
        }.launchIn(viewModelScope)
    }
}