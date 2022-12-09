package com.maulana.fitella.utils

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BoxWithLayout(content: @Composable BoxScope.()->Unit){
    Box(Modifier.fillMaxSize()) {
        content()
    }
}
