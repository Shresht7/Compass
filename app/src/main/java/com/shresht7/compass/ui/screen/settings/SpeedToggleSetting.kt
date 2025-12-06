package com.shresht7.compass.ui.screen.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shresht7.compass.settings.AppSettingsManager
import kotlinx.coroutines.launch

@Composable
fun SpeedToggleSetting(appSettingsManager: AppSettingsManager) {
    val scope = rememberCoroutineScope()
    val isEnabled by appSettingsManager.speedDisplayEnabled.collectAsState(initial = true)

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Display Speed",
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isEnabled,
            onCheckedChange = { checked ->
                scope.launch { appSettingsManager.setSpeedDisplayEnabled(checked) }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpeedToggleSetting() {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Display Speed",
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = true,
            onCheckedChange = { }
        )
    }
}
