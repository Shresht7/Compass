package com.shresht7.compass

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.shresht7.compass.settings.AppSettingsManager
import com.shresht7.compass.navigation.Screen
import com.shresht7.compass.ui.screen.CompassScreen
import com.shresht7.compass.ui.screen.SettingsScreen
import com.shresht7.compass.ui.theme.CompassTheme
import com.shresht7.compass.viewModel.CompassViewModel

class MainActivity : ComponentActivity() {

    /**
     * The [CompassViewModel] instance used by this activity to manage and provide compass-related data
     * to the UI. It's responsible for handling sensor data, location updates, and user settings.
     */
    private lateinit var viewModel: CompassViewModel

    /**
     * Manages the application's settings, providing an interface to read and write
     * user preferences, such as theme, location format, and other display options.
     * It is initialized in `onCreate` and passed to the relevant ViewModels and Composables
     * that need access to app settings.
     */
    private lateinit var appSettingsManager: AppSettingsManager

    /**
     * Activity result launcher for requesting location permissions.
     *
     * This launcher handles the result of the permission request dialog. If either fine or coarse
     * location permission is granted by the user, it proceeds to call `startLocationUpdatesWithPermissionCheck`
     * to begin receiving location updates.
     */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
        ) {
            startLocationUpdatesWithPermissionCheck()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appSettingsManager = AppSettingsManager(applicationContext)
        viewModel = CompassViewModel(application, appSettingsManager)

        setContent {
            CompassTheme {
                val backStack = rememberNavBackStack(Screen.Home)
                NavDisplay(
                    backStack,
                    entryProvider = entryProvider {
                        entry<Screen.Home> { CompassScreen(viewModel, backStack) }
                        entry<Screen.Settings> { SettingsScreen(backStack, appSettingsManager) }
                    }
                )
            }
        }

        checkLocationPermission()
    }

    /**
     * Checks if the app has been granted location permissions.
     *
     * If permission is already granted (either fine or coarse), it proceeds to start location updates.
     * If permission has not been granted, it launches a system dialog to request the necessary
     * permissions from the user. The result of this request is handled by [requestPermissionLauncher].
     */
    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                startLocationUpdatesWithPermissionCheck()
            }

            else -> {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    /**
     * Checks for location permissions (fine or coarse) again before starting location updates.
     * This function serves as a final safeguard to ensure that the app has the necessary
     * permissions before calling `viewModel.startLocationUpdates()`. It is called after
     * permissions have been explicitly granted, either through a previous check or by the user
     * through a permission request dialog.
     */
    private fun startLocationUpdatesWithPermissionCheck() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.startLocationUpdates()
        }
    }
}