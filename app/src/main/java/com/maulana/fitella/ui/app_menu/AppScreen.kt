package com.maulana.fitella.ui.app_menu
import com.maulana.fitella.R

sealed class AppScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home : AppScreen(
        route = "home_screen", title = "Home", icon = R.drawable.home,
    )
    object Explore : AppScreen(
        route = "explore_screen", title = "Explore", icon = R.drawable.explore,
    )
    object Empty : AppScreen(
        route = "EMPTY", title = "", icon = R.drawable.add,
    )
    object Event : AppScreen(
        route = "event_screen", title = "Event", icon = R.drawable.event,
    )
    object Profile : AppScreen(
        route = "profile_screen", title = "Profile", icon = R.drawable.unclicked,
    )
}
