package com.shresht7.compass.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CompassLocation(
    latitude: Double,
    longitude: Double,
    altitude: Double?,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Latitude")
            Text(text = String.format("%.4f", latitude))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Longitude")
            Text(text = String.format("%.4f", longitude))
        }
        if (altitude != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Altitude")
                Text(text = String.format("%.2f m", altitude))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 480)
fun CompassLocationPreview() {
    CompassLocation(
        latitude = 40.7128,
        longitude = -74.0060,
        altitude = 121.4,
    )
}
