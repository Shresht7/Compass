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

    val sensorDelay: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[sensorDelayKey] ?: SensorManager.SENSOR_DELAY_FASTEST // Default to fastest
        }

    suspend fun setSensorDelay(delay: Int) {
        context.dataStore.edit { settings ->
            settings[sensorDelayKey] = delay
        }
    }
}
