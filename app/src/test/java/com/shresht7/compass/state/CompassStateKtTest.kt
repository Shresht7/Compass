package com.shresht7.compass.state

import junit.framework.TestCase
import org.junit.Test

class CompassStateKtTest {

    @Test
    fun `direction   with North Azimuth`() {
        // Given an azimuth of 0.0f, the function should return "N".
        val actual = CompassState(0f).direction()
        val expectedDirection = "N"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with North East Azimuth`() {
        // Given an azimuth of 45.0f, the function should return "NE".
        val actual = CompassState(45f).direction()
        val expectedDirection = "NE"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with East Azimuth`() {
        // Given an azimuth of 90.0f, the function should return "E".
        val actual = CompassState(90f).direction()
        val expectedDirection = "E"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with South East Azimuth`() {
        // Given an azimuth of 135.0f, the function should return "SE".
        val actual = CompassState(135f).direction()
        val expectedDirection = "SE"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with South Azimuth`() {
        // Given an azimuth of 180.0f, the function should return "S".
        val actual = CompassState(180f).direction()
        val expectedDirection = "S"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with South West Azimuth`() {
        // Given an azimuth of 225.0f, the function should return "SW".
        val actual = CompassState(225f).direction()
        val expectedDirection = "SW"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with West Azimuth`() {
        // Given an azimuth of 270.0f, the function should return "W".
        val actual = CompassState(270f).direction()
        val expectedDirection = "W"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with North West Azimuth`() {
        // Given an azimuth of 315.0f, the function should return "NW"
        val actual = CompassState(315f).direction()
        val expectedDirection = "NW"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with North lower bound`() {
        // Test the lower boundary for North (e.g., 337.5f) to ensure it correctly maps to "N".
        val actual = CompassState(337.5f).direction()
        val expectedDirection = "N"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with North upper bound`() {
        // Test the upper boundary for North (e.g., 22.49f) to ensure it correctly maps to "N".
        val actual = CompassState(22.49f).direction()
        val expectedDirection = "N"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with North East lower bound`() {
        // Test the lower boundary for North-East (e.g., 22.5f) to ensure it correctly maps to "NE".
        val actual = CompassState(22.5f).direction()
        val expectedDirection = "NE"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with North East upper bound`() {
        // Test the upper boundary for North-East (e.g., 67.49f) to ensure it correctly maps to "NE".
        val actual = CompassState(67.49f).direction()
        val expectedDirection = "NE"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with azimuth greater than 360`() {
        // Test with an azimuth value greater than 360 (e.g., 380f) to see how it's handled; it should fall into the 'N' range.
        val actual = CompassState(380f).direction()
        val expectedDirection = "N"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `direction   with negative azimuth`() {
        // Test with a negative azimuth value (e.g., -10f); it should fall into the 'N' range based on the logic.
        val actual = CompassState(-10f).direction()
        val expectedDirection = "N"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `degrees   with zero azimuth`() {
        // Given an azimuth of 0.0f, the function should return "0.0°".
        val actual = CompassState(0f).degrees()
        val expectedDirection = "0.0°"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `degrees   with positive integer azimuth`() {
        // Given an azimuth of 45f, the function should return "45.0°".
        val actual = CompassState(45f).degrees()
        val expectedDirection = "45.0°"
        TestCase.assertEquals(expectedDirection, actual)
    }

    @Test
    fun `degrees   with multi decimal float azimuth`() {
        // Given an azimuth like 98.765f, the function should correctly round and format it to "98.8°".
        val actual = CompassState(98.765f).degrees()
        val expectedDirection = "98.8°"
        TestCase.assertEquals(expectedDirection, actual)

    }

    @Test
    fun `degrees   with single decimal float azimuth`() {
        // Given an azimuth of 123.4f, the function should return "123.4°".
        val actual = CompassState(123.4f).degrees()
        val expectedDirection = "123.4°"
        TestCase.assertEquals(expectedDirection, actual)

    }

    @Test
    fun `degrees   with large azimuth value`() {
        // Test with a large azimuth value (e.g., 9999.99f) to ensure it formats correctly to "10000.0°".
        val actual = CompassState(9999.99f).degrees()
        val expectedDirection = "10000.0°"
        TestCase.assertEquals(expectedDirection, actual)

    }

    @Test
    fun `degrees   with negative azimuth`() {
        // Test with a negative azimuth value (e.g., -15.55f) to ensure it formats correctly to "-15.6°".
        val actual = CompassState(-15.55f).degrees()
        val expectedDirection = "-15.6°"
        TestCase.assertEquals(expectedDirection, actual)

    }

}