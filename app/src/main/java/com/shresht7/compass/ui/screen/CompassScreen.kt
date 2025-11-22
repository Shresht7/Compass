package com.shresht7.compass.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompassScreen() {
    Text("Compass")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompassScreenPreview() {
    CompassScreen()
}