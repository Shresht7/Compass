package com.shresht7.compass.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shresht7.compass.settings.AppSettingsManager
import com.shresht7.compass.ui.components.BackButton
import com.shresht7.compass.ui.screen.settings.geolocation.AddressToggleSetting
import com.shresht7.compass.ui.screen.settings.geolocation.AltitudeToggleSetting
import com.shresht7.compass.ui.screen.settings.geolocation.LatitudeToggleSetting
import com.shresht7.compass.ui.screen.settings.geolocation.LongitudeToggleSetting
import com.shresht7.compass.ui.screen.settings.heading.HeadingToggleSetting
import com.shresht7.compass.ui.screen.settings.heading.MagneticFieldToggleSetting
import com.shresht7.compass.ui.screen.settings.heading.SpeedToggleSetting
import com.shresht7.compass.ui.screen.settings.sensors.SensorDelaySetting

/**
 * The settings screen of the application.
 *
 * This composable provides a UI for users to configure various application settings,
 * such as the sensor delay for the compass.
 *
 * @param appSettingsManager The manager for application settings, used to read and write preferences.
 * @param onNavBack The callback to be invoked when the navigation back button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    appSettingsManager: AppSettingsManager,
    onNavBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = { BackButton(onClick = onNavBack) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // Sensors Section
            Text(
                text = "Sensors",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            SensorDelaySetting(appSettingsManager)

            // Heading Section
            Text(
                text = "Heading",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            HeadingToggleSetting(appSettingsManager)
            SpeedToggleSetting(appSettingsManager)
            MagneticFieldToggleSetting(appSettingsManager)

            // GeoLocation Section
            Text(
                text = "GeoLocation",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            LatitudeToggleSetting(appSettingsManager)
            LongitudeToggleSetting(appSettingsManager)
            AltitudeToggleSetting(appSettingsManager)
            AddressToggleSetting(appSettingsManager)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    val appSettingsManager = AppSettingsManager(LocalContext.current)
    SettingsScreen(appSettingsManager)
}