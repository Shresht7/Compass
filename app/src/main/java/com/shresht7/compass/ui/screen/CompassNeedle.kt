package com.shresht7.compass.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.shresht7.compass.R

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
