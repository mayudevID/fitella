package com.maulana.fitella.ui.onboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.maulana.fitella.theme.Color5
import com.maulana.fitella.theme.FitellaTheme
import com.maulana.fitella.theme.Poppins
import com.maulana.fitella.ui.login.LoginActivity
import com.maulana.fitella.ui.widget.BallDown
import com.maulana.fitella.ui.widget.BallUp

class OnboardActivity : ComponentActivity() {
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
            ContentOnboard()
        }
    }

    @Composable
    fun ContentOnboard() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dp(24F)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.frame_onboard),
                contentDescription = "Frame Onboard",
                modifier = Modifier.size(width = Dp(350F), height = Dp(203F))
            )
            Spacer(modifier = Modifier.height(Dp(45F)))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Let’s start living a",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp, color = Color.Black
                )
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "healthy life", fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp, color = Color2
                )
                Text(
                    text = ".",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 36.sp, color = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(Dp(20F)))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Organize your healthy food, sports",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp, color = Color.Black
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "schedule, and sports events with ",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp, color = Color.Black
                    )
                )
                Text(
                    text = "Fitella",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp, color = Color2
                    )
                )
                Text(
                    text = "!",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp, color = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(Dp(40F)))
            Button(
                onClick = {
                    startActivity(Intent(this@OnboardActivity, LoginActivity::class.java))
                    finishAffinity()
                },
                shape = RoundedCornerShape(100),
                modifier = Modifier.size(Dp(200f), Dp(61f)),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color5)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "GET STARTED",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White,
                        )
                    )
                    Spacer(modifier = Modifier.width(Dp(8f)))
                    Image(
                        painter = painterResource(R.drawable.next),
                        contentDescription = "Next Button"
                    )
                }
            }
        }
    }
}