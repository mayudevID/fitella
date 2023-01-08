package com.maulana.fitella.ui.app_menu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.FitellaTheme
import com.maulana.fitella.ui.app_menu.widget.CustomMainActivityPopup

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitellaTheme(this, darkTheme = false) {
                val navController = rememberNavController()
                val showDialog = remember { mutableStateOf(false) }
                var rotateValue by remember {
                    mutableStateOf(0f)
                }
                val rotateValueState by animateFloatAsState(
                    targetValue = rotateValue, animationSpec = tween(
                        durationMillis = 500
                    )
                )

                if (showDialog.value) {
                    CustomMainActivityPopup(
                        activity = this@MainActivity,
                        setShowDialog = { showDialog.value = it },
                    )
                }

                rotateValue = if (showDialog.value) {
                    45f
                } else {
                    0f
                }

                Scaffold(
                    bottomBar = {
                        BottomBar(navController = navController)
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                showDialog.value = !showDialog.value
                            },
                            backgroundColor = Color2,
                            content = {
                                Icon(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .graphicsLayer {
                                            rotationZ = rotateValueState
                                        },
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