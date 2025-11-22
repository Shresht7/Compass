package com.shresht7.compass.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shresht7.compass.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompassScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CompassView(
            azimuth = 0f,
            modifier = Modifier.size(300.dp)
        )
    }
}

@Composable
fun CompassView(
    azimuth: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CompassNeedle(
            rotation = azimuth,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CompassNeedle(
    rotation: Float,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.compass_needle_original),
        contentDescription = "Compass Needle",
        modifier = modifier.rotate(rotation)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompassScreenPreview() {
    CompassScreen()
}