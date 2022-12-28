package com.maulana.fitella.ui.app_menu.profile.sub_menu

import android.app.Dialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
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
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins
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
        Card() {
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SsPreview() {
   SettingsScreen(setShowDialog = {})
}