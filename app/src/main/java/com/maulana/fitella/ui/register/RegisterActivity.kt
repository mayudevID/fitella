package com.maulana.fitella.ui.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.maulana.fitella.theme.FitellaTheme
import com.maulana.fitella.ui.widget.BallDown
import com.maulana.fitella.ui.widget.BallUp

class RegisterActivity : ComponentActivity() {
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
            //ContentLogin()
        }
    }
}