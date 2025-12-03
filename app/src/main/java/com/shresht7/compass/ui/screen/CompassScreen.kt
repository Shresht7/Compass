package com.shresht7.compass.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.shresht7.compass.R
import com.shresht7.compass.sensor.Location
import com.shresht7.compass.state.CompassState
import com.shresht7.compass.state.degrees
import com.shresht7.compass.state.direction
import com.shresht7.compass.state.magneticField
import com.shresht7.compass.state.speed
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
        CompassHeading(
            degrees = compassState.degrees(),
            direction = compassState.direction(),
            speed = compassState.speed(),
            magneticField = compassState.magneticField(),
            modifier = Modifier.fillMaxWidth(),
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CompassBackground(
                rotation = 0f,
                modifier = Modifier.fillMaxSize()
            )
            CompassNeedle(
                rotation = -compassState.azimuth,
                modifier = Modifier.fillMaxSize()
            )
        }

        CompassLocation(
            latitude = compassState.location.latitude,
            longitude = compassState.location.longitude,
            altitude = if (compassState.location.hasAltitude) compassState.location.altitude else null,
            address = compassState.location.address,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompassScreenPreview() {
    val previewLocation = Location(
        latitude = 40.7128,
        longitude = -74.0060,
        address = "New York, NY, USA",
        hasAltitude = true,
        altitude = 121.4
    )
    CompassView(compassState = CompassState(azimuth = 10f, magneticField = 49.1f, location = previewLocation))
}
