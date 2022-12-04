package com.maulana.fitella.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.maulana.fitella.R

@Composable
fun BallUp() {
    Image(
        painter = painterResource(R.drawable.up),
        contentDescription = "Background 1",
        modifier = Modifier
            .absoluteOffset(Dp(75F), Dp(-150F))
            .size(Dp(481F))
            .scale(1.25F)
    )
}

@Composable
fun BallDown() {
    Image(
        painter = painterResource(R.drawable.down),
        contentDescription = "Background 2",
        modifier = Modifier
            .absoluteOffset(Dp(-70F), Dp(400F))
            .size(Dp(481F))
            .scale(1.25F)
    )
}