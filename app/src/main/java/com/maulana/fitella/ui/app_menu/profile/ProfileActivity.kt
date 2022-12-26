package com.maulana.fitella.ui.app_menu.profile

import android.provider.ContactsContract.Profile
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins

enum class ProfileTab { PhotoActivity, Settings, Saved, Report }

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    var selectedProfileTab by remember { mutableStateOf(ProfileTab.PhotoActivity) }

    Surface() {
        Box(Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(304.dp)
                    .alpha(0.75f),
                painter = painterResource(R.drawable.temp_bg_profile),
                contentScale = ContentScale.Crop,
                contentDescription = "Background Profile"
            )
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "kahfa.gaming", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Image(
                        modifier = Modifier.align(Alignment.CenterEnd).clickable {

                        },
                        imageVector = ImageVector.vectorResource(R.drawable.profile_pick),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(11.dp))
                Image(
                    modifier = Modifier.size(93.dp),
                    painter = painterResource(R.drawable.temp_kahfa),
                    contentDescription = "Prof Pict User"
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Muhammad Safier", style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "If you can dream about it, you can do it", style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 9.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Jakarta, Indonesia", style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 9.sp,
                        color = Color.White
                    )
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(525.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color2
            ) {
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(465.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color.White
            ) {
                when (selectedProfileTab) {
                    ProfileTab.PhotoActivity -> PhotoActivityScreen()
                    ProfileTab.Settings -> PhotoActivityScreen()
                    ProfileTab.Saved -> PhotoActivityScreen()
                    ProfileTab.Report ->  PhotoActivityScreen()
                }
            }
        }
    }
}

