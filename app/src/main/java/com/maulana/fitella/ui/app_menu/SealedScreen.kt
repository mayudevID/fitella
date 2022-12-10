package com.maulana.fitella.ui.app_menu

sealed class AppScreen(val route: String) {
    object Home: AppScreen(route = "home_screen")
    object Explore: AppScreen(route = "explore_screen")
    object Event: AppScreen(route = "event_screen")
    object Profile: AppScreen(route = "profile_screen")
}
