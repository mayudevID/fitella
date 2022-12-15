package com.maulana.fitella.ui.app_menu.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Color5
import com.maulana.fitella.theme.Poppins

@Preview(showBackground = true)
@Composable
fun ExploreScreen() {
    Surface() {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 10.dp, horizontal = 14.dp)
            ) {
                Column() {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.49.dp),
                        elevation = 0.dp,
                        backgroundColor = Color(0xFFF7F7F7),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 13.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                ImageVector.vectorResource(com.maulana.fitella.R.drawable.clarity_search_line),
                                modifier = Modifier.size(10.dp),
                                contentDescription = "Search Icon"
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            BoxSearch()
                        }
                    }
                    Spacer(modifier = Modifier.height(11.dp))
                    BoxTab()
                }
            }
        }

    }
}

@Composable
fun BoxSearch() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Box(modifier = Modifier.fillMaxWidth()) {
        if (searchQuery.text.isEmpty() || searchQuery.text == "") {
            Text(
                text = "Search",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = Poppins,
                    color = Color(0xFF858585),
                    fontStyle = FontStyle.Italic,
                    baselineShift = BaselineShift(-0.21f)
                ),
            )
        }
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchQuery,
            cursorBrush = Brush.verticalGradient(
                0.00f to Color.Transparent,
                0.20f to Color5,
                0.80f to Color5,
                1.00f to Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.Light,
                fontFamily = Poppins,
                color = Color.Black,
            ),
            onValueChange = {
                if (it.text.length <= 50) searchQuery = it
            },
        )
    }
}

@Composable
fun BoxTab() {
    var isSelected by remember { mutableStateOf(0) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                isSelected = 0
            },
            modifier = Modifier
                .weight(0.333f)
                .height(29.dp),
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(if (isSelected == 0) Color2 else Color(0xFFE7E7E7)),
        ) {
            Text(
                text = "All",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 9.sp,
                    color = if (isSelected == 0) Color.White else Color.Black,
                    textAlign = TextAlign.Center,
                ),
            )
        }
        Spacer(modifier = Modifier.width(8.51.dp))
        Button(
            onClick = {
                isSelected = 1
            },
            modifier = Modifier
                .weight(0.333f)
                .height(29.dp),
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(if (isSelected == 1) Color2 else Color(0xFFE7E7E7)),
        ) {
            Text(
                text = "Jogging", style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 9.sp,
                    color = if (isSelected == 1) Color.White else Color.Black
                )
            )
        }
        Spacer(modifier = Modifier.width(8.51.dp))
        Button(
            onClick = {
                isSelected = 2
            },
            modifier = Modifier
                .weight(0.333f)
                .height(29.dp),
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(if (isSelected == 2) Color2 else Color(0xFFE7E7E7)),
        ) {
            Text(
                text = "Color run", style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 9.sp,
                    color = if (isSelected == 2) Color.White else Color.Black
                )
            )
        }
    }
}