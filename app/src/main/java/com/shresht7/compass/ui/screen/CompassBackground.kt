package com.shresht7.compass.ui.screen

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassBackground(
    rotation: Float,
    modifier: Modifier = Modifier
) {
    val textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.White.toArgb()
        textAlign = Paint.Align.CENTER
    }
    val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")

    Canvas(modifier = modifier) {
        val center = this.center
        val radius = size.minDimension / 3f
        textPaint.textSize = radius * 0.12f

        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.save()
            canvas.nativeCanvas.rotate(rotation, center.x, center.y)

            // Outer dial text
            directions.forEachIndexed { i, dir ->
                val angleDegrees = i * 45f - 90f
                val angleRad = Math.toRadians(angleDegrees.toDouble())

                val textRadius = radius * 0.88f
                val x = center.x + textRadius * cos(angleRad).toFloat()
                val y = center.y + textRadius * sin(angleRad).toFloat()

                // Adjust text position to be centered
                val textY = y - (textPaint.ascent() + textPaint.descent()) / 2

                canvas.nativeCanvas.save()
                // Rotate text to be aligned with the dial
                canvas.nativeCanvas.rotate(angleDegrees + 90, x, y)
                canvas.nativeCanvas.drawText(dir, x, textY, textPaint)
                canvas.nativeCanvas.restore()
            }

            // Inner circle
            val innerCircleRadius = radius * 0.5f
            drawCircle(
                color = Color.Transparent,
                radius = innerCircleRadius,
                center = center
            )
            drawCircle(
                color = Color.Gray,
                radius = innerCircleRadius,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )

            // Inner marks for N, E, W, S
            for (i in 0..3) {
                val angleDegrees = i * 90f - 90f // N, E, S, W
                val angleRad = Math.toRadians(angleDegrees.toDouble())
                val isMajor = i % 2 == 0 // N, S are at -90 and 90
                val markLength = innerCircleRadius * if (isMajor) 0.35f else 0.2f

                val startRadius = innerCircleRadius * 0.95f
                val endRadius = startRadius - markLength

                val startX = center.x + startRadius * cos(angleRad).toFloat()
                val startY = center.y + startRadius * sin(angleRad).toFloat()

                val endX = center.x + endRadius * cos(angleRad).toFloat()
                val endY = center.y + endRadius * sin(angleRad).toFloat()

                drawLine(
                    color = Color.White,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = if (isMajor) 4.dp.toPx() else 2.dp.toPx()
                )
            }

            canvas.nativeCanvas.restore()
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun CompassBackgroundPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        CompassBackground(rotation = 0f, modifier = Modifier.fillMaxSize())
    }
}
