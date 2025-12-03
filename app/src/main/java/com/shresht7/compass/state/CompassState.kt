package com.shresht7.compass.state

import com.shresht7.compass.sensor.Location

/**
 * Represents the current state of the compass.
 *
 * @property azimuth The current azimuth value in degrees, representing the direction the top of the
 * device is pointing, where 0 is North, 90 is East, 180 is South, and 270 is West.
 * @property location The current location of the device.
 * @property magneticField The magnetic field strength in micro-Tesla (μT).
 */
data class CompassState(
    val azimuth: Float = 0f,
    val location: Location = Location(),
    val magneticField: Float = 0f
)

/**
 * The angular width of each cardinal and intercardinal direction sector in degrees.
 * The compass is divided into 8 sectors (N, NE, E, SE, S, SW, W, NW),
 * and each sector spans 45 degrees (360 / 8 = 45).
 */
const val SECTOR_ANGLE = 45f

/**
 * Calculates the cardinal direction (N, NE, E, etc.) based on the azimuth.
 *
 * The compass is divided into 8 sectors of 45 degrees each. This function determines
 * which sector the current azimuth falls into and returns the corresponding
 * cardinal direction abbreviation.
 *
 * For example:
 * - An azimuth of 0° is North ("N").
 * - An azimuth of 45° is North-East ("NE").
 * - An azimuth of 90° is East ("E").
 *
 * The calculation handles the wrap-around at 360°/0° for the North sector.
 *
 * @return A string representing the cardinal direction (e.g., "N", "NE", "E", "SE", "S", "SW", "W", "NW").
 */
fun CompassState.direction(): String {
    val halfSector = SECTOR_ANGLE / 2f
    return when {
        (azimuth >= 360f - halfSector || azimuth < 0f + halfSector) -> "N"
        (azimuth in 45f - halfSector..45f + halfSector) -> "NE"
        (azimuth in 90f - halfSector..90f + halfSector) -> "E"
        (azimuth in 135f - halfSector..135f + halfSector) -> "SE"
        (azimuth in 180f - halfSector..180f + halfSector) -> "S"
        (azimuth in 225f - halfSector..225f + halfSector) -> "SW"
        (azimuth in 270f - halfSector..270f + halfSector) -> "W"
        (azimuth in 315f - halfSector..315f + halfSector) -> "NW"
        else -> "N"
    }
}

/**
 * Formats the azimuth value from the [CompassState] into a human-readable string
 * with one decimal place, appended with the degree symbol.
 *
 * For example, an azimuth of `45.123f` would be formatted as `"45.1°"`.
 *
 * @return A [String] representing the formatted degrees.
 */
fun CompassState.degrees(): String {
    return String.format("%.1f°", azimuth)
}

/**
 * Formats the speed value from the [CompassState] into a human-readable string
 * with one decimal place, appended with " m/s".
 *
 * For example, a speed of `1.23f` would be formatted as `"1.2 m/s"`.
 *
 * @return A [String] representing the formatted speed.
 */
fun CompassState.speed(): String {
    return String.format("%.1f m/s", location.speed)
}

/**
 * Formats the magnetic field strength value from the [CompassState] into a human-readable string
 * with one decimal place, appended with " μT".
 *
 * For example, a magnetic field of `49.123f` would be formatted as `"49.1 μT"`.
 *
 * @return A [String] representing the formatted magnetic field strength.
 */
fun CompassState.magneticField(): String {
    return String.format("%.1f μT", magneticField)
}
