package com.maulana.fitella.ui.app_menu.profile.sub_menu

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Color3
import com.maulana.fitella.theme.Poppins
import com.maulana.fitella.ui.app_menu.event.gridTempData

@Composable
fun SavedEventScreen(
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
                text = "Saved Event", style = TextStyle(
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(21.dp),
            horizontalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            items(9) { pos ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                        },
                    elevation = 0.dp,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Card(
                            modifier = Modifier
                                .height(101.37.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Image(
                                painter = painterResource(gridTempData[pos]),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Event Grid"
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "G.C Marathon", style = TextStyle(
                                fontSize = 10.sp,
                                fontFamily = Poppins,
                                color = Color3,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                modifier = Modifier.size(8.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.star),
                                contentDescription = "Star"
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "5.0", style = TextStyle(
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp,
                                    color = Color(0xFF1B1A57),
                                    baselineShift = BaselineShift(-0.21f)
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "(88 co)", style = TextStyle(
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 9.sp,
                                    color = Color(0xFF9C9999),
                                    baselineShift = BaselineShift(-0.21f),
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }
                        Text(
                            text = "Ticket prices start from IDR 50.000, domiciled in Bali.",
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Light,
                                color = Color.Black,
                                fontSize = 9.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SePreview() {
    SavedEventScreen(setShowDialog = {})
}