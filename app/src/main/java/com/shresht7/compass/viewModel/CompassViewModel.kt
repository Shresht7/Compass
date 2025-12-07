package com.shresht7.compass.viewModel

import android.Manifest
import android.app.Application
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shresht7.compass.sensor.CompassSensor
import com.shresht7.compass.sensor.LocationManager
import com.shresht7.compass.settings.AppSettingsManager
import com.shresht7.compass.state.CompassState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ViewModel for the compass screen.
 *
 * This class serves as the central point for managing the UI state and data for the compass feature.
 * It integrates data from various sources:
 *  - [CompassSensor]: Provides azimuth (heading) and magnetic field strength.
 *  - [LocationManager]: Provides geographical location data (latitude, longitude, altitude, speed).
 *  - [AppSettingsManager]: Manages user preferences for sensor update speed and visibility of
 *    different data points on the screen.
 *
 * The ViewModel exposes the combined state through [compassState] and individual display
 * preferences as separate [StateFlow]s, which the UI can observe to stay up-to-date.
 *
 * @param application The application context, required for accessing system services like sensors
 *                    and location.
 * @param appSettingsManager An instance of [AppSettingsManager] to observe user settings.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CompassViewModel(application: Application, val appSettingsManager: AppSettingsManager) : AndroidViewModel(application) {
    private val compassSensor = CompassSensor(application)
    private val locationManager = LocationManager(application)

    private var locationUpdatesJob: Job? = null

    private val _compassState = MutableStateFlow(CompassState())
    val compassState: StateFlow<CompassState> = _compassState.asStateFlow()

    private val _latitudeEnabled = MutableStateFlow(true)
    val latitudeEnabled: StateFlow<Boolean> = _latitudeEnabled.asStateFlow()

    private val _longitudeEnabled = MutableStateFlow(true)
    val longitudeEnabled: StateFlow<Boolean> = _longitudeEnabled.asStateFlow()

    private val _altitudeEnabled = MutableStateFlow(true)
    val altitudeEnabled: StateFlow<Boolean> = _altitudeEnabled.asStateFlow()

    private val _addressEnabled = MutableStateFlow(true)
    val addressEnabled: StateFlow<Boolean> = _addressEnabled.asStateFlow()

    private val _headingDisplayEnabled = MutableStateFlow(true)
    val headingDisplayEnabled: StateFlow<Boolean> = _headingDisplayEnabled.asStateFlow()

    private val _magneticFieldDisplayEnabled = MutableStateFlow(true)
    val magneticFieldDisplayEnabled: StateFlow<Boolean> = _magneticFieldDisplayEnabled.asStateFlow()

    private val _speedDisplayEnabled = MutableStateFlow(true)
    val speedDisplayEnabled: StateFlow<Boolean> = _speedDisplayEnabled.asStateFlow()

    init {
        // Observe sensor delay and update compass flow
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
                e.printStackTrace()
            }
            .launchIn(viewModelScope)

        // Location updates are always active, individual visibility controlled by settings
        startLocationUpdates()

        // Observe individual location display settings
        appSettingsManager.latitudeEnabled
            .onEach { enabled -> _latitudeEnabled.value = enabled }
            .launchIn(viewModelScope)

        appSettingsManager.longitudeEnabled
            .onEach { enabled -> _longitudeEnabled.value = enabled }
            .launchIn(viewModelScope)

        appSettingsManager.altitudeEnabled
            .onEach { enabled -> _altitudeEnabled.value = enabled }
            .launchIn(viewModelScope)

        appSettingsManager.addressEnabled
            .onEach { enabled -> _addressEnabled.value = enabled }
            .launchIn(viewModelScope)

        appSettingsManager.headingDisplayEnabled
            .onEach { enabled -> _headingDisplayEnabled.value = enabled }
            .launchIn(viewModelScope)

        appSettingsManager.magneticFieldDisplayEnabled
            .onEach { enabled -> _magneticFieldDisplayEnabled.value = enabled }
            .launchIn(viewModelScope)

        appSettingsManager.speedDisplayEnabled
            .onEach { enabled -> _speedDisplayEnabled.value = enabled }
            .launchIn(viewModelScope)
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun startLocationUpdates() {
        locationUpdatesJob = locationManager.getLocationFlow()
            .onEach { location ->
                _compassState.value = _compassState.value.copy(location = location)
            }
            .launchIn(viewModelScope)
    }

    private fun stopLocationUpdates() {
        locationUpdatesJob?.cancel()
        locationUpdatesJob = null
        _compassState.value =
            _compassState.value.copy(location = com.shresht7.compass.sensor.Location())
    }
}