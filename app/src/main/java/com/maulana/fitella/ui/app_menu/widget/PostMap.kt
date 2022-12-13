package com.maulana.fitella.ui.app_menu.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.theme.Poppins

@Preview
@Composable
fun PostMap() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = Color(0xFFF2F2F2),
        //Color(0xFFECECEC),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(31.dp)
                        .clip(CircleShape),
                    painter = painterResource(com.maulana.fitella.R.drawable.temp_1),
                    contentDescription = "Prof Pict"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "justin_27",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                    )
                    Text(
                        text = "Jalan K.H Mas Mansyur",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        ),
                    )
                }
                Image(
                    modifier = Modifier
                        .size(width = 4.dp, height = 15.dp)
                        .clip(CircleShape),
                    painter = painterResource(com.maulana.fitella.R.drawable.detail_post),
                    contentDescription = "Prof Pict"
                )
            }
            Spacer(modifier = Modifier.height(11.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(com.maulana.fitella.R.drawable.temp_map),
                contentDescription = "Photo"
            )
            Spacer(modifier = Modifier.height(9.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(15.dp),
                    imageVector = ImageVector.vectorResource(com.maulana.fitella.R.drawable.like),
                    contentDescription = "Like",
                )
                Spacer(modifier = Modifier.width(11.dp))
                Image(
                    modifier = Modifier.size(15.dp),
                    imageVector = ImageVector.vectorResource(com.maulana.fitella.R.drawable.comment),
                    contentDescription = "Like",
                )
                Spacer(modifier = Modifier.width(11.dp))
                Image(
                    modifier = Modifier.size(15.dp),
                    imageVector = ImageVector.vectorResource(com.maulana.fitella.R.drawable.share),
                    contentDescription = "Like",
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "27 likes", style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins,
                        color = Color.Black,
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "justin_27 ",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    ),
                )
                Text(
                    text = "sedang jogging nih bersama teman",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    ),
                )
            }
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "Just Now", style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 6.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            )
        }
    }
}