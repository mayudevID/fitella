package com.maulana.fitella.ui.app_menu.widget

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins
import com.maulana.fitella.ui.app_menu.profile.ProfileTab

@Composable
fun CustomProfilePickPopup(
    setShowDialog: (Boolean) -> Unit,
    cardOrange: (Float) -> Unit,
    cardWhite: (Float) -> Unit,
    profileAlpha: (Float) -> Unit,
    selectedProfileTab: (ProfileTab) -> Unit,
    profileTab: ProfileTab
) {
    Popup(
        //onDismissRequest = {}
        alignment = Alignment.TopCenter,
        offset = IntOffset(x = 300, y = if (profileTab != ProfileTab.PhotoActivity) 96 else 48),
        properties = PopupProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        CustomProfilePickPopupPUI(
            setShowDialog = setShowDialog,
            cardOrange = cardOrange,
            cardWhite = cardWhite,
            profileAlpha = profileAlpha,
            selectedProfileTab = selectedProfileTab,
            profileTab = profileTab,
        )
    }
}

@Composable
fun CustomProfilePickPopupPUI(
    setShowDialog: (Boolean) -> Unit,
    cardOrange: (Float) -> Unit,
    cardWhite: (Float) -> Unit,
    profileAlpha: (Float) -> Unit,
    selectedProfileTab: (ProfileTab) -> Unit,
    profileTab: ProfileTab
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.size(96.dp, 128.dp),
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceAround
        ) {
            //Photo Activity
            Text(
                modifier = Modifier.clickable {
                    setShowDialog(false)
                    if (profileTab != ProfileTab.PhotoActivity) {
                        cardOrange(0.66f)
                        cardWhite(0.586f)
                        profileAlpha(1f)
                    }
                    selectedProfileTab(ProfileTab.PhotoActivity)
                },
                text = "Post",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (profileTab == ProfileTab.PhotoActivity) Color.Black else Color2
                )
            )
            //Settings Activity
            Text(
                modifier = Modifier.clickable {
                    setShowDialog(false)
                    if (profileTab == ProfileTab.PhotoActivity) {
                        cardOrange(0.98f)
                        cardWhite(0.96f)
                        profileAlpha(0f)
                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                selectedProfileTab(ProfileTab.Settings)
                            },
                            240
                        )
                    } else {
                        selectedProfileTab(ProfileTab.Settings)
                    }
                },
                text = "Settings", style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (profileTab == ProfileTab.Settings) Color.Black else Color2
                )
            )
            //Saved Activity
            Text(
                modifier = Modifier.clickable {
                    setShowDialog(false)
                    if (profileTab == ProfileTab.PhotoActivity) {
                        cardOrange(0.98f)
                        cardWhite(0.96f)
                        profileAlpha(0f)
                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                selectedProfileTab(ProfileTab.Saved)
                            },
                            240
                        )
                    } else {
                        selectedProfileTab(ProfileTab.Saved)
                    }
                },
                text = "Saved", style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (profileTab == ProfileTab.Saved) Color.Black else Color2
                )
            )
            //Report Activity
            Text(
                modifier = Modifier.clickable {
                    setShowDialog(false)
                    if (profileTab == ProfileTab.PhotoActivity) {
                        cardOrange(0.98f)
                        cardWhite(0.96f)
                        profileAlpha(0f)
                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                selectedProfileTab(ProfileTab.Report)
                            },
                            240
                        )
                    } else {
                        selectedProfileTab(ProfileTab.Report)
                    }
                },
                text = "Report", style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (profileTab == ProfileTab.Report) Color.Black else Color2
                )
            )
        }
    }
}

//@SuppressLint("UnrememberedMutableState")
//@Preview(name = "Custom Dialog")
//@Composable
//fun MyProfilePickDialogUIPreview() {
//    val showDialog = remember { mutableStateOf(false) }
//    var cardOrangeValue = 0f
//    var cardWhiteValue = 0f
//    var profileAlpha = 0f
//
//    CustomProfilePickPopup(
//        setShowDialog = { showDialog.value = it },
//        cardOrange = { cardOrangeValue = it },
//        cardWhite = { cardWhiteValue = it },
//        profileAlpha = { profileAlpha = it }
//    )
//}