package com.maulana.fitella.ui.app_menu.profile.sub_menu

import android.app.Dialog
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Color5
import com.maulana.fitella.theme.Poppins
import com.maulana.fitella.ui.app_menu.MainActivity
import com.maulana.fitella.ui.app_menu.profile.ProfileTab

@Composable
fun SettingsScreen(
    setShowDialog: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = "Settings", style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color2
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(color = Color2)
                    .clickable {
                        setShowDialog(true)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.profile_pick),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFF2F2F2),
            elevation = 0.dp,
            shape = RoundedCornerShape(13.dp),

            ) {
            Row(Modifier.padding(17.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(56.dp),
                    painter = painterResource(R.drawable.temp_kahfa),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Muhammad Safier Al Kahfa", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            color = Color.Black
                        ), maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "kahfa.gaming",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = Color(0xFF989DB4)
                        ),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(13.dp))
        Card(
            Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFF2F2F2),
            elevation = 0.dp,
            shape = RoundedCornerShape(13.dp),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp, horizontal = 24.dp)
            ) {
                Text(
                    text = "PROFILE",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color(0xFF929292)
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Name",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = Color.Black,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Muhammad Safier Al Kahfa", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = Color(0xFF989DB4),
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Weight",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = Color.Black,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "60 kg", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = Color(0xFF989DB4),
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Email",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = Color.Black,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "kahfakun22@gmail.com", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = Color(0xFF989DB4),
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = Color.Black,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "**********", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = Color(0xFF989DB4),
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Address",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = Color.Black,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Grand Batavia", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = Color(0xFF989DB4),
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Change Picture",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "PREFERENCES",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color(0xFF929292)
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Privacy Settings",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Notification",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        color = Color.Black,
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(Modifier.fillMaxWidth()) {
            Button(shape = RoundedCornerShape(100),
                modifier = Modifier
                    .size(Dp(218f), Dp(40f))
                    .shadow(
                        shape = RoundedCornerShape(100),
                        elevation = 7.dp,
                        ambientColor = Color(0xFFE22020),
                        spotColor = Color(0xFFE22020)
                    ).align(Alignment.Center),
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE22020)),
                onClick = {
                }) {
                Text(
                    text = "LOGOUT", style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SsPreview() {
    SettingsScreen(setShowDialog = {})
}