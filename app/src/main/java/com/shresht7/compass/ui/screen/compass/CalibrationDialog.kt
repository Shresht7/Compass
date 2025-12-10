package com.shresht7.compass.ui.screen.compass

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CalibrationDialog(onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Compass Calibration") },
        text = {
            Text(
                "To calibrate your compass, move your device in a figure-eight motion several times " +
                        "until the compass reading stabilizes. Ensure you are away from strong magnetic " +
                        "fields (like speakers or large metal objects)."
            )
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}