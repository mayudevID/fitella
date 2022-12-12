package com.maulana.fitella.ui.app_menu

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins
import com.maulana.fitella.R

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        AppScreen.Home,
        AppScreen.Explore,
        AppScreen.Empty,
        AppScreen.Event,
        AppScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: AppScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isClicked = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true;

    fun changeIcon(): Int {
        return if (isClicked) {
            when (screen) {
                is AppScreen.Home -> R.drawable.home_clicked
                is AppScreen.Explore -> R.drawable.explore_clicked
                is AppScreen.Empty -> 0
                is AppScreen.Event -> R.drawable.event_clicked
                is AppScreen.Profile -> screen.icon
            }
        } else {
            screen.icon
        }
    }

    BottomNavigationItem(
        label = {
            Text(
                text = screen.title, style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        },
        icon = {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(changeIcon()),
                contentDescription = "Navigation Icon"
            )
        },
        selected = isClicked,
        selectedContentColor = Color2,
        unselectedContentColor = Color.Black,
        onClick = {
            if (screen.route != AppScreen.Empty.route) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    )
}
