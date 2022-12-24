package com.maulana.fitella.ui.app_menu

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maulana.fitella.ui.app_menu.event.EventScreen
import com.maulana.fitella.ui.app_menu.explore.ExploreScreen
import com.maulana.fitella.ui.app_menu.home.HomeScreen
import com.maulana.fitella.ui.app_menu.profile.ProfileScreen

@Composable
fun SetupNavGraph(
    activity: MainActivity,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = AppScreen.Home.route) {
        composable(
            route = AppScreen.Home.route
        ) {
            HomeScreen()
        }
        composable(
            route = AppScreen.Explore.route
        ) {
            ExploreScreen()
        }
        composable(
            route = AppScreen.Event.route
        ) {
            EventScreen(activity)
        }
        composable(
            route = AppScreen.Profile.route
        ) {
            ProfileScreen()
        }
    }
}