package com.shresht7.compass.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.shresht7.compass.settings.AppSettingsManager
import com.shresht7.compass.ui.components.BackButton

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