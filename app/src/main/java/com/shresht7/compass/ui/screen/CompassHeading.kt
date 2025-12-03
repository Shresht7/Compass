package com.shresht7.compass.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CompassHeading(
    degrees: String,
    direction: String,
    speed: String,
    magneticField: String,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp)
) {
    Column(
        modifier = modifier.padding(16.dp, top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement,
        ) {
            Text(text = "$degrees $direction", style = MaterialTheme.typography.displaySmall)
            VerticalDivider(thickness = 2.dp, modifier = Modifier.height(48.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = speed, style = MaterialTheme.typography.titleSmall)
                Text(text = magneticField, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompassHeadingPreview() {
    CompassHeading(degrees = "123.4", direction = "N", speed = "1.2 m/s", magneticField = "49.1 μT")
}