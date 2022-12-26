package com.maulana.fitella.ui.app_menu.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup

@Composable
fun CustomProfilePickPopup(setShowDialog: (Boolean) -> Unit) {
    Dialog(
        onDismissRequest = {}
        //alignment = Alignment.BottomCenter,
        //offset = IntOffset(x = 0,y = -256),
    ) {
        CustomProfilePickPopupPUI(setShowDialog = setShowDialog)
    }
}

@Composable
fun CustomProfilePickPopupPUI(setShowDialog: (Boolean) -> Unit) {

}