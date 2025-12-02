package com.shresht7.compass.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shresht7.compass.R
import com.shresht7.compass.state.CompassState
import com.shresht7.compass.viewModel.CompassViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompassScreen(
    viewModel: CompassViewModel
) {
    val compassState by viewModel.compassState.collectAsState()
    CompassView(
        compassState = compassState,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CompassView(
    compassState: CompassState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CompassNeedle(
                rotation = -compassState.azimuth,
                modifier = Modifier.fillMaxSize()
            )
        }
        CompassLocation(
            latitude = compassState.location.latitude,
            longitude = compassState.location.longitude,
            altitude = if (compassState.location.hasAltitude) compassState.location.altitude else null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CompassNeedle(
    rotation: Float,
    modifier: Modifier = Modifier
) {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.compass_needle_original),
        contentDescription = "Compass Needle",
        modifier = modifier.rotate(rotation)
    )
}

@Preview
@Composable
fun CompassNeedlePreview() {
    CompassNeedle(rotation = 15f)
}

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
@Preview
fun CompassLocationPreview() {
    CompassLocation(
        latitude = 40.7128,
        longitude = -74.0060,
        altitude = 121.4,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompassScreenPreview() {
    CompassView(compassState = CompassState(azimuth = 10f))
}
