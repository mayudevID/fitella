package com.maulana.fitella.ui.app_menu.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.maulana.fitella.theme.Color1
import com.maulana.fitella.theme.Poppins

@Composable
fun CustomMainActivityPopup(setShowDialog: (Boolean) -> Unit) {
    Popup(
        alignment = Alignment.BottomCenter,
        offset = IntOffset(x = 0,y = -256),
    ) {
        CustomMainActivityPopupUI(setShowDialog = setShowDialog)
    }
}

@Composable
fun CustomMainActivityPopupUI(setShowDialog: (Boolean) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(160.dp, 120.dp),
        elevation = 8.dp,
        backgroundColor = Color1,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        setShowDialog(false)
                    }, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Count Calories", style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontFamily = Poppins
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(com.maulana.fitella.R.drawable.cd_icon_1),
                    contentDescription = "Dialog Ic 1"
                )
            }
            Divider(color = Color.White, thickness = 1.dp)
            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        setShowDialog(false)
                    }, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Run", style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontFamily = Poppins
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(com.maulana.fitella.R.drawable.cd_icon_2),
                    contentDescription = "Dialog Ic 2"
                )
            }
            Divider(color = Color.White, thickness = 1.dp)
            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        setShowDialog(false)
                    }, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Post", style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontFamily = Poppins
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(com.maulana.fitella.R.drawable.cd_icon_3),
                    contentDescription = "Dialog Ic 3"
                )
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun MyDialogUIPreview() {
    val showDialog = remember { mutableStateOf(false) }

    CustomMainActivityPopup(setShowDialog = { showDialog.value = it })
}