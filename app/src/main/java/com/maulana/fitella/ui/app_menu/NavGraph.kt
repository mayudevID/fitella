package com.maulana.fitella.ui.app_menu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = AppScreen.Home.route) {
        composable(
            route = AppScreen.Home.route
        ) {

        }
        composable(
            route = AppScreen.Explore.route
        ) {

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