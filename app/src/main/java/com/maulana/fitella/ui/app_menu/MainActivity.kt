package com.maulana.fitella.ui.app_menu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.FitellaTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           FitellaTheme(this, darkTheme = false) {
               val navController = rememberNavController()
               Scaffold(
                   bottomBar = {
                       BottomBar(navController = navController)
                   },
                   floatingActionButton = {
                       FloatingActionButton(
                           onClick = { },
                           backgroundColor = Color2,
                           content = {
                               Icon(
                                   modifier = Modifier.size(22.dp),
                                   painter = painterResource(R.drawable.add),
                                   contentDescription = "Create",
                                   tint = Color.White
                               )
                           }
                       )
                   },
                   floatingActionButtonPosition = FabPosition.Center,
                   isFloatingActionButtonDocked = true,
               ) {
                   SetupNavGraph(navController = navController, activity = this@MainActivity)
               }
           }
        }
    }
}