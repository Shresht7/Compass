package com.shresht7.compass.ui.screen

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
        }
    }
}

@Composable
fun SensorDelaySetting(appSettingsManager: AppSettingsManager) {
    val scope = rememberCoroutineScope()
    val sensorDelay by appSettingsManager.sensorDelay.collectAsState(initial = SensorManager.SENSOR_DELAY_FASTEST)
    val sensorDelayOptions = listOf("UI", "Normal", "Game", "Fastest")
    
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
            Text(sensorDelayOptions[
                when(sensorDelay) {
                    SensorManager.SENSOR_DELAY_UI -> 0
                    SensorManager.SENSOR_DELAY_NORMAL -> 1
                    SensorManager.SENSOR_DELAY_GAME -> 2
                    SensorManager.SENSOR_DELAY_FASTEST -> 3
                    else -> 3 // Default to fastest
                }
            ])
        }
        Slider(
            value = when(sensorDelay) {
                SensorManager.SENSOR_DELAY_UI -> 0f
                SensorManager.SENSOR_DELAY_NORMAL -> 1f
                SensorManager.SENSOR_DELAY_GAME -> 2f
                SensorManager.SENSOR_DELAY_FASTEST -> 3f
                else -> 3f // Default to fastest
            },
            onValueChange = {
                scope.launch {
                    val newDelay = when(it.toInt()) {
                        0 -> SensorManager.SENSOR_DELAY_UI
                        1 -> SensorManager.SENSOR_DELAY_NORMAL
                        2 -> SensorManager.SENSOR_DELAY_GAME
                        3 -> SensorManager.SENSOR_DELAY_FASTEST
                        else -> SensorManager.SENSOR_DELAY_FASTEST
                    }
                    appSettingsManager.setSensorDelay(newDelay)
                }
            },
            valueRange = 0f..3f,
            steps = 2
        )
    }
}