package com.maulana.fitella.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Space
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.*
import com.maulana.fitella.ui.app_menu.MainActivity
import com.maulana.fitella.ui.register.RegisterActivity
import com.maulana.fitella.ui.widget.BallDown
import com.maulana.fitella.ui.widget.BallUp
import com.maulana.fitella.utils.BoxWithLayout

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
            ColumnBottom()
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
            Spacer(modifier = Modifier.height(Dp(8f)))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(10.dp),
                    painter = painterResource(R.drawable.unclicked),
                    contentDescription = "Remember Button"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Remember me",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = ColorGray,
                        baselineShift = BaselineShift(-0.21f)
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Forgot Password",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = ColorGray,
                        baselineShift = BaselineShift(-0.21f)
                    ),
                )
            }
            Spacer(modifier = Modifier.height(43.dp))
            Button(shape = RoundedCornerShape(100),
                modifier = Modifier
                    .size(Dp(218f), Dp(40f))
                    .shadow(
                        shape = RoundedCornerShape(100),
                        elevation = 7.dp,
                        ambientColor = Color5,
                        spotColor = Color5
                    ),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color5),
                onClick = {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finishAffinity()
                }) {
                Text(
                    text = "LOGIN", style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                )
            }
            Spacer(modifier = Modifier.height(19.dp))
            Text(
                text = "Or", style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = ColorGray,
                )
            )
            Row() {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Button(modifier = Modifier.size(54.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        onClick = { }) {}
                    Image(
                        modifier = Modifier.size(38.67.dp),
                        painter = painterResource(R.drawable.google),
                        contentDescription = "Google"
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Button(modifier = Modifier.size(54.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3B5998)),
                    onClick = { }) {
                    Image(
                        modifier = Modifier.size(14.27.dp, 28.88.dp),
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = "Facebook"
                    )
                }
            }
            Spacer(modifier = Modifier.height(120.dp))
        }
    }

    @Composable
    fun EmailBox() {
        var email by remember { mutableStateOf(TextFieldValue("")) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(39.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = Color5, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            if (email.text.isEmpty() || email.text == "") {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.5.dp),
                    text = "Email",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = ColorGray,
                        baselineShift = BaselineShift(-0.21f)
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
                    baselineShift = BaselineShift(-0.21f)
                ),
                value = email,
                onValueChange = {
                    if (it.text.length <= 50) email = it
                },
            )
        }
    }

    @Composable
    fun PasswordBox() {
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var isPasswordVisible by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(39.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = Color5, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            if (password.text.isEmpty() || password.text == "") {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.5.dp),
                    text = "Password",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = ColorGray,
                        baselineShift = BaselineShift(-0.21f)
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
                        baselineShift = BaselineShift(-0.21f)
                    ),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    value = password,
                    onValueChange = {
                        if (it.text.length <= 50) password = it

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

    @Composable
    fun ColumnBottom() {
        BoxWithLayout {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Don’t have any account?", style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        color = ColorGray,
                        baselineShift = BaselineShift(-0.21f)
                    )
                )
                Text(
                    text = "Sign Up.", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color2,
                        baselineShift = BaselineShift(-0.21f)
                    ),

                    modifier = Modifier.clickable {
                        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                    }
                )
                Spacer(modifier = Modifier.height(21.dp))
            }
        }
    }
}