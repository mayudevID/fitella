package com.maulana.fitella.ui.app_menu.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
//            LazyColumn {
//                items() {
//
//                }
//            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp)
                    .height(57.dp),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .background(Color2, RoundedCornerShape(100))
                        .padding(horizontal = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fitella!",
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            ImageVector.vectorResource(com.maulana.fitella.R.drawable.notification),
                            modifier = Modifier.size(17.dp),
                            contentDescription = "Notifikasi",
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Image(
                            ImageVector.vectorResource(com.maulana.fitella.R.drawable.message),
                            modifier = Modifier.size(17.dp),
                            contentDescription = "Pesan",
                        )
                    }
                }
            }
        }
    }
}