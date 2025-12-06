package com.shresht7.compass.ui.screen.settings

import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shresht7.compass.settings.AppSettingsManager
import kotlinx.coroutines.launch

// Define the mapping between slider position (0-3) and SensorManager constants
private val SLIDER_TO_SENSOR_DELAY_MAP = mapOf(
    0 to SensorManager.SENSOR_DELAY_UI,
    1 to SensorManager.SENSOR_DELAY_NORMAL,
    2 to SensorManager.SENSOR_DELAY_GAME,
    3 to SensorManager.SENSOR_DELAY_FASTEST,
)

// Define the mapping between SensorManager constants and display names
private val SENSOR_DELAY_TO_DISPLAY_NAME_MAP = mapOf(
    SensorManager.SENSOR_DELAY_FASTEST to "Fastest",
    SensorManager.SENSOR_DELAY_GAME to "Fast",
    SensorManager.SENSOR_DELAY_NORMAL to "Normal",
    SensorManager.SENSOR_DELAY_UI to "Slow"
)

/**
 * A composable that provides a UI for configuring the sensor delay setting.
 *
 * This composable uses a slider to allow the user to select one of four predefined
 * sensor delay options: Fastest, Game, Normal, and UI. The selected delay is
 * persisted using the `AppSettingsManager`.
 *
 * @param appSettingsManager The manager for application settings.
 */
@Composable
fun SensorDelaySetting(appSettingsManager: AppSettingsManager) {
    val scope = rememberCoroutineScope()
    val sensorDelay by appSettingsManager.sensorDelay.collectAsState(initial = SensorManager.SENSOR_DELAY_FASTEST)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sensor Delay")
            Text(SENSOR_DELAY_TO_DISPLAY_NAME_MAP[sensorDelay] ?: "Normal") // Display current delay name
        }
        Slider(
            value = SLIDER_TO_SENSOR_DELAY_MAP.entries.find { it.value == sensorDelay }?.key?.toFloat() ?: 2f,
            onValueChange = { sliderValue ->
                scope.launch {
                    val newDelay = SLIDER_TO_SENSOR_DELAY_MAP[sliderValue.toInt()] ?: SensorManager.SENSOR_DELAY_NORMAL
                    appSettingsManager.setSensorDelay(newDelay)
                }
            },
            valueRange = 0f..3f,
            steps = 2
        )
    }
}
