package com.shresht7.compass.ui.screen.compass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CompassLocation(
    latitude: Double,
    longitude: Double,
    altitude: Double?,
    address: String?,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Latitude", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.secondary)
                Text(
                    text = String.format("%.2f°", latitude),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Longitude", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.secondary)
                Text(
                    text = String.format("%.2f°", longitude),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            if (altitude != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Altitude", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.secondary)
                    Text(
                        text = String.format("%.2f m", altitude),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }

        if (address != null) {
            Box(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)) {
                Text(
                    text = address,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
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
        address = "New York, NY, USA"
    )
}