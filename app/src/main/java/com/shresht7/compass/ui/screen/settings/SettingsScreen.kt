package com.shresht7.compass.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.shresht7.compass.ui.components.BackButton
import com.shresht7.compass.settings.AppSettingsManager
import kotlinx.coroutines.launch
import android.hardware.SensorManager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.Switch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

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
 * The settings screen of the application.
 *
 * This composable provides a UI for users to configure various application settings,
 * such as the sensor delay for the compass.
 *
 * @param backStack The navigation back stack for handling navigation actions.
 * @param appSettingsManager The manager for application settings, used to read and write preferences.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(backStack: NavBackStack<NavKey>, appSettingsManager: AppSettingsManager) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    BackButton(onClick = { backStack.removeLastOrNull() })
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            SensorDelaySetting(appSettingsManager)
            LatitudeToggleSetting(appSettingsManager)
            LongitudeToggleSetting(appSettingsManager)
            AltitudeToggleSetting(appSettingsManager)
            AddressToggleSetting(appSettingsManager)
        }
    }
}

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



/**
 * A composable that provides a UI for toggling latitude display.
 *
 * @param appSettingsManager The manager for application settings.
 */
@Composable
fun LatitudeToggleSetting(appSettingsManager: AppSettingsManager) {
    val scope = rememberCoroutineScope()
    val latitudeEnabled by appSettingsManager.latitudeEnabled.collectAsState(initial = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Show Latitude")
        Switch(
            checked = latitudeEnabled,
            onCheckedChange = { enabled ->
                scope.launch {
                    appSettingsManager.setLatitudeEnabled(enabled)
                }
            }
        )
    }
}

/**
 * A composable that provides a UI for toggling longitude display.
 *
 * @param appSettingsManager The manager for application settings.
 */
@Composable
fun LongitudeToggleSetting(appSettingsManager: AppSettingsManager) {
    val scope = rememberCoroutineScope()
    val longitudeEnabled by appSettingsManager.longitudeEnabled.collectAsState(initial = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Show Longitude")
        Switch(
            checked = longitudeEnabled,
            onCheckedChange = { enabled ->
                scope.launch {
                    appSettingsManager.setLongitudeEnabled(enabled)
                }
            }
        )
    }
}

/**
 * A composable that provides a UI for toggling altitude display.
 *
 * @param appSettingsManager The manager for application settings.
 */
@Composable
fun AltitudeToggleSetting(appSettingsManager: AppSettingsManager) {
    val scope = rememberCoroutineScope()
    val altitudeEnabled by appSettingsManager.altitudeEnabled.collectAsState(initial = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Show Altitude")
        Switch(
            checked = altitudeEnabled,
            onCheckedChange = { enabled ->
                scope.launch {
                    appSettingsManager.setAltitudeEnabled(enabled)
                }
            }
        )
    }
}

/**
 * A composable that provides a UI for toggling address display.
 *
 * @param appSettingsManager The manager for application settings.
 */
@Composable
fun AddressToggleSetting(appSettingsManager: AppSettingsManager) {
    val scope = rememberCoroutineScope()
    val addressEnabled by appSettingsManager.addressEnabled.collectAsState(initial = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Show Address")
        Switch(
            checked = addressEnabled,
            onCheckedChange = { enabled ->
                scope.launch {
                    appSettingsManager.setAddressEnabled(enabled)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    // Preview for SettingsScreen would require mocking NavBackStack and AppSettingsManager
    // For simplicity, we can preview individual settings components
    Column {
        SensorDelaySetting(appSettingsManager = AppSettingsManager(
            LocalContext.current
        ))
        LatitudeToggleSetting(appSettingsManager = AppSettingsManager(
            LocalContext.current
        ))
        LongitudeToggleSetting(appSettingsManager = AppSettingsManager(
            LocalContext.current
        ))
        AltitudeToggleSetting(appSettingsManager = AppSettingsManager(
            LocalContext.current
        ))
        AddressToggleSetting(appSettingsManager = AppSettingsManager(
            LocalContext.current
        ))
    }
}