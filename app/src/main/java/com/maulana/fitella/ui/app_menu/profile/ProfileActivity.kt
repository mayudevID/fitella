package com.maulana.fitella.ui.app_menu.profile

import android.provider.ContactsContract.Profile
import android.widget.Space
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.graphicsLayer
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
import com.maulana.fitella.ui.app_menu.profile.sub_menu.PhotoActivityScreen
import com.maulana.fitella.ui.app_menu.profile.sub_menu.SettingsScreen
import com.maulana.fitella.ui.app_menu.widget.CustomProfilePickPopup

enum class ProfileTab { PhotoActivity, Settings, Saved, Report }

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    var selectedProfileTab by remember { mutableStateOf(ProfileTab.PhotoActivity) }
    val showDialog = remember { mutableStateOf(false) }

    var profileAlphaValue by remember {
        mutableStateOf(1f)
    }
    val profileAlphaValueState by animateFloatAsState(
        targetValue = profileAlphaValue, animationSpec = tween(
            durationMillis = 240
        )
    )

    var cardOrangeValue by remember {
        mutableStateOf(0.66f)
    }
    val cardWOrangeValueState by animateFloatAsState(
        targetValue = cardOrangeValue, animationSpec = tween(
            durationMillis = 240
        )
    )

    var cardWhiteValue by remember {
        mutableStateOf(0.586f)
    }
    val cardWWhiteValueState by animateFloatAsState(
        targetValue = cardWhiteValue, animationSpec = tween(
            durationMillis = 240
        )
    )

    if (showDialog.value) {
        CustomProfilePickPopup(
            setShowDialog = { showDialog.value = it },
            cardOrange = { cardOrangeValue = it },
            cardWhite = { cardWhiteValue = it },
            profileAlpha = { profileAlphaValue = it },
            selectedProfileTab = { selectedProfileTab = it },
            profileTab = selectedProfileTab
        )
    }

    Surface() {
        Box(Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .alpha(0.75f),
                painter = painterResource(R.drawable.temp_bg_profile),
                contentScale = ContentScale.Crop,
                contentDescription = "Background Profile"
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.34f)
                    .graphicsLayer {
                        alpha = profileAlphaValueState
                    },
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Spacer(modifier = Modifier.height(24.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
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
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                if (selectedProfileTab == ProfileTab.PhotoActivity) {
                                    showDialog.value = !showDialog.value
                                }
                            },
                        imageVector = ImageVector.vectorResource(R.drawable.profile_pick),
                        contentDescription = null
                    )
                }
                //Spacer(modifier = Modifier.height(11.dp))
                Image(
                    modifier = Modifier.size(93.dp),
                    painter = painterResource(R.drawable.temp_kahfa),
                    contentDescription = "Prof Pict User"
                )
                //Spacer(modifier = Modifier.height(15.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Muhammad Safier", style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "If you can dream about it, you can do it\nJakarta, Indonesia",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(cardWOrangeValueState)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color2
            ) {
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(cardWWhiteValueState)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color.White
            ) {
                AnimatedContent(targetState = selectedProfileTab) {
                    targetState ->
                    when (targetState) {
                        ProfileTab.PhotoActivity -> PhotoActivityScreen()
                        ProfileTab.Settings -> SettingsScreen(
                            setShowDialog = { showDialog.value = it }
                        )
                        ProfileTab.Saved -> null
                        ProfileTab.Report -> null
                    }
                }
            }
        }
    }
}

