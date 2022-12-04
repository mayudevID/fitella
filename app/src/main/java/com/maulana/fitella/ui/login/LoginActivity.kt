package com.maulana.fitella.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.maulana.fitella.R
import com.maulana.fitella.theme.FitellaTheme
import com.maulana.fitella.ui.widget.BallDown
import com.maulana.fitella.ui.widget.BallUp

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitellaTheme(this, darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    ContentUi()
                }
            }
        }
    }

    @Preview
    @Composable
    fun ContentUi() {
        Box(modifier = Modifier.fillMaxSize()) {
            BallUp()
            BallDown()
            ContentLogin()
        }
    }

    @Composable
    fun ContentLogin() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(Dp(226.34f), Dp(233f) ),
                painter = painterResource(R.drawable.login_frame),
                contentDescription = "Login Frame"
            )
        }
    }
}