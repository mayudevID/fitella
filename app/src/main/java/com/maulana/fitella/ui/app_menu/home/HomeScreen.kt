package com.maulana.fitella.ui.app_menu.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.maulana.fitella.ui.app_menu.widget.PostInfo
import com.maulana.fitella.ui.app_menu.widget.PostMap
import com.maulana.fitella.ui.app_menu.widget.PostPhoto

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Surface {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp)
                    .height(57.dp),
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    backgroundColor = Color2,
                    shape = RoundedCornerShape(100)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
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
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                items(3) {
                    when(it) {
                        0 -> PostMap()
                        1 -> PostInfo()
                        2 -> PostPhoto()
                    }
                }
            }
        }
    }
}