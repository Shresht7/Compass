package com.shresht7.compass.settings

import android.content.Context
import android.hardware.SensorManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.booleanPreferencesKey

/**
 * Extension property on [Context] to provide a single instance of [DataStore] for the application's
 * settings. It uses `preferencesDataStore` to create and manage a [DataStore] instance
 * named "settings". This allows for easy, consistent access to the app's persistent preferences
 * from anywhere with a [Context].
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Manages application settings using Jetpack DataStore.
 *
 * This class provides a way to persist and retrieve user-defined settings,
 * such as the sensor update speed for the compass. It abstracts the DataStore
 * implementation, offering simple-to-use properties and methods for accessing
 * and modifying settings.
 *
 * @param context The application context, used to access the DataStore instance.
 */
class AppSettingsManager(private val context: Context) {

    private val sensorDelayKey = intPreferencesKey("sensor_delay")
    private val locationEnabledKey = booleanPreferencesKey("location_enabled")
    private val latitudeEnabledKey = booleanPreferencesKey("latitude_enabled")
    private val longitudeEnabledKey = booleanPreferencesKey("longitude_enabled")
    private val altitudeEnabledKey = booleanPreferencesKey("altitude_enabled")
    private val addressEnabledKey = booleanPreferencesKey("address_enabled")

    val sensorDelay: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[sensorDelayKey] ?: SensorManager.SENSOR_DELAY_FASTEST // Default to fastest
        }

    val locationEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[locationEnabledKey] ?: true // Default to true
        }

    val latitudeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[latitudeEnabledKey] ?: true // Default to true
        }

    val longitudeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[longitudeEnabledKey] ?: true // Default to true
        }

    val altitudeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[altitudeEnabledKey] ?: true // Default to true
        }

    val addressEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[addressEnabledKey] ?: true // Default to true
        }

    suspend fun setSensorDelay(delay: Int) {
        context.dataStore.edit { settings ->
            settings[sensorDelayKey] = delay
        }
    }

    suspend fun setLocationEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[locationEnabledKey] = enabled
        }
    }

    suspend fun setLatitudeEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[latitudeEnabledKey] = enabled
        }
    }

    suspend fun setLongitudeEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[longitudeEnabledKey] = enabled
        }
    }

    suspend fun setAltitudeEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[altitudeEnabledKey] = enabled
        }
    }

    suspend fun setAddressEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[addressEnabledKey] = enabled
        }
    }
}
