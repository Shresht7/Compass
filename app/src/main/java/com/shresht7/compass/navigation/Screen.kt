package com.shresht7.compass.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String, val title: String): NavKey {
    @Serializable
    data object Home: Screen("home", "Home")
    @Serializable
    data object Settings: Screen("settings", "Settings")
}