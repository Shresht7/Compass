package com.shresht7.compass.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * A data class that holds the compass sensor readings.
 *
 * @property azimuth The direction of the magnetic north in degrees, where 0° is North, 90° is East, 180° is South, and 270° is West.
 * @property magneticField The strength of the geomagnetic field in micro-Tesla (μT).
 */
data class CompassData(val azimuth: Float = 0f, val magneticField: Float = 0f)

class CompassSensor(context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometerReadings = FloatArray(3)
    private val magnetometerReadings = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    fun getCompassFlow(sensorDelay: Int): Flow<CompassData> = callbackFlow {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if (accelerometer == null || magnetometer == null) {
            close(IllegalStateException("Accelerometer or Magnetometer not available"))
            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                // Collect Sensor Readings
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        System.arraycopy(event.values, 0, accelerometerReadings, 0, event.values.size)
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        System.arraycopy(event.values, 0, magnetometerReadings, 0, event.values.size)
                    }
                }

                // Calculate Rotation Matrix
                SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReadings, magnetometerReadings)

                // Get Orientation Angles
                SensorManager.getOrientation(rotationMatrix, orientationAngles)

                // Convert Azimuth from radian to degrees
                val azimuthInDegrees = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                val azimuth = (azimuthInDegrees + 360) % 360

                val magneticField = sqrt(magnetometerReadings[0].pow(2) + magnetometerReadings[1].pow(2) + magnetometerReadings[2].pow(2))

                trySend(CompassData(azimuth = azimuth, magneticField = magneticField))
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, sensorDelay)
        sensorManager.registerListener(listener, magnetometer, sensorDelay)

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}