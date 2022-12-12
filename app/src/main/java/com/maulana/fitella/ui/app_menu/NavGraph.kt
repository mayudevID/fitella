package com.maulana.fitella.ui.app_menu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maulana.fitella.ui.app_menu.explore.ExploreScreen
import com.maulana.fitella.ui.app_menu.home.HomeScreen

@Composable
fun SetupNavGraph(
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

        }
        composable(
            route = AppScreen.Profile.route
        ) {

        }
    }
}