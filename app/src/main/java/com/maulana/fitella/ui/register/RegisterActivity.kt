package com.maulana.fitella.ui.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.FitellaTheme
import com.maulana.fitella.theme.Poppins
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

    @Preview(showBackground = true)
    @Composable
    fun ContentUi() {
        Box(modifier = Modifier.fillMaxSize()) {
            BallUp()
            BallDown()
            ContentRegister()
        }
    }

    @Composable
    fun ContentRegister() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 33.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(Dp(171f), Dp(166f)),
                painter = painterResource(R.drawable.register_frame),
                contentDescription = "Register Frame"
            )
            Text(
                text = "Register", style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    color = Color2
                )
            )
        }
    }
}