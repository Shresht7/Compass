package com.shresht7.compass.ui.screen.settings.geolocation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shresht7.compass.settings.AppSettingsManager
import kotlinx.coroutines.launch

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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Display Longitude")
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
