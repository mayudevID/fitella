package com.maulana.fitella.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Color5
import com.maulana.fitella.theme.FitellaTheme
import com.maulana.fitella.theme.Poppins
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

    @Preview(showBackground = true)
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
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 33.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(Dp(226.34f), Dp(233f)),
                painter = painterResource(R.drawable.login_frame),
                contentDescription = "Login Frame"
            )
            Text(
                text = "Login", style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    color = Color2
                )
            )
            Spacer(modifier = Modifier.height(Dp(32f)))
            EmailBox()
            Spacer(modifier = Modifier.height(Dp(32f)))
            PasswordBox()
        }
    }

    @Composable
    fun EmailBox() {
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var isHintEmailVisible by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(39.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = Color5, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            if (isHintEmailVisible) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.5.dp),
                    text = "Email",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = Color(0xFF9A9A9A)
                    )
                )
            }
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.5.dp),
                cursorBrush = Brush.verticalGradient(
                    0.00f to Color.Transparent,
                    0.20f to Color5,
                    0.80f to Color5,
                    1.00f to Color.Transparent
                ),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Poppins,
                    color = Color.Black,
                ),
                value = email,
                onValueChange = {
                    if (it.text.length <= 50) email = it
                    isHintEmailVisible = it.text.isEmpty() || it.text == ""
                },
            )
        }
    }

    @Composable
    fun PasswordBox() {
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var isHintPasswordVisible by remember { mutableStateOf(true) }
        var isPasswordVisible by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(39.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = Color5, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            if (isHintPasswordVisible) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.5.dp),
                    text = "Password",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = Color(0xFF9A9A9A)
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier.weight(1f),
                    cursorBrush = Brush.verticalGradient(
                        0.00f to Color.Transparent,
                        0.20f to Color5,
                        0.80f to Color5,
                        1.00f to Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = Color.Black,
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    value = password,
                    onValueChange = {
                        if (it.text.length <= 50) password = it
                        isHintPasswordVisible = it.text.isEmpty() || it.text == ""
                    },
                )
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .clickable {
                            isPasswordVisible = !isPasswordVisible
                        },
                    painter = painterResource(if (isPasswordVisible) R.drawable.eyeline else R.drawable.eye),
                    contentDescription = "EYE",
                    alignment = Alignment.Center,
                )
            }
        }
    }
}