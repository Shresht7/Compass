package com.shresht7.compass.ui.screen.compass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.shresht7.compass.navigation.Screen
import com.shresht7.compass.sensor.Location
import com.shresht7.compass.settings.AppSettingsManager
import com.shresht7.compass.state.CompassState
import com.shresht7.compass.state.degrees
import com.shresht7.compass.state.direction
import com.shresht7.compass.state.magneticField
import com.shresht7.compass.state.speed
import com.shresht7.compass.viewModel.CompassViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompassScreen(
    viewModel: CompassViewModel,
    backStack: NavBackStack<NavKey>
) {
    val compassState by viewModel.compassState.collectAsState()
    val latitudeEnabled by viewModel.latitudeEnabled.collectAsState()
    val longitudeEnabled by viewModel.longitudeEnabled.collectAsState()
    val altitudeEnabled by viewModel.altitudeEnabled.collectAsState()
    val addressEnabled by viewModel.addressEnabled.collectAsState()
    val headingDisplayEnabled by viewModel.headingDisplayEnabled.collectAsState()
    val magneticFieldDisplayEnabled by viewModel.magneticFieldDisplayEnabled.collectAsState()
    val speedDisplayEnabled by viewModel.speedDisplayEnabled.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = { backStack.add(Screen.Settings) }) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { innerPadding ->
            CompassView(
                compassState = compassState,
                latitudeEnabled = latitudeEnabled,
                longitudeEnabled = longitudeEnabled,
                altitudeEnabled = altitudeEnabled,
                addressEnabled = addressEnabled,
                headingDisplayEnabled = headingDisplayEnabled,
                magneticFieldDisplayEnabled = magneticFieldDisplayEnabled,
                speedDisplayEnabled = speedDisplayEnabled,
                modifier = Modifier.padding(innerPadding)
            )
        }
}

@Composable
fun CompassView(
    compassState: CompassState,
    latitudeEnabled: Boolean = true,
    longitudeEnabled: Boolean = true,
    altitudeEnabled: Boolean = true,
    addressEnabled: Boolean = true,
    headingDisplayEnabled: Boolean = true,
    magneticFieldDisplayEnabled: Boolean = true,
    speedDisplayEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CompassHeading(
            degrees = compassState.degrees(0),
            direction = compassState.direction(),
            speed = compassState.speed(),
            magneticField = compassState.magneticField(),
            headingDisplayEnabled = headingDisplayEnabled,
            magneticFieldDisplayEnabled = magneticFieldDisplayEnabled,
            speedDisplayEnabled = speedDisplayEnabled,
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
            latitudeEnabled = latitudeEnabled,
            longitudeEnabled = longitudeEnabled,
            altitudeEnabled = altitudeEnabled,
            addressEnabled = addressEnabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompassViewPreview() {
    val previewLocation = Location(
        latitude = 40.7128,
        longitude = -74.0060,
        address = "New York, NY, USA",
        hasAltitude = true,
        altitude = 121.4
    )
    CompassView(
        compassState = CompassState(
            azimuth = 10f,
            magneticField = 49.1f,
            location = previewLocation
        ),
    )
}

