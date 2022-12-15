package com.maulana.fitella.ui.app_menu.event

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.animation.AnimationUtils.lerp
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Color3
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

val tempData = listOf(
    com.maulana.fitella.R.drawable.temp_event_1,
    com.maulana.fitella.R.drawable.temp_event_2,
    com.maulana.fitella.R.drawable.temp_event_3
)

@Composable
fun EventScreen() {
    Surface() {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 10.dp)
            ) {
                SliderEventTop()
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SliderEventTop() {
    val pagerStateEvent = rememberPagerState(initialPage = 0)

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            tween<Float>(600)
            pagerStateEvent.animateScrollToPage(
                page = (pagerStateEvent.currentPage + 1) % (pagerStateEvent.pageCount)
            )
        }
    }

    Box(
        modifier = Modifier
            .height(174.dp)
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerStateEvent,
            modifier = Modifier
                .height(174.dp)
                .fillMaxWidth(),
            itemSpacing = (-50).dp,
            count = 3,
            verticalAlignment = Alignment.CenterVertically,
        ) { page ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            0.85f,
                            1f,
                            1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            0.5f,
                            1f,
                            1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxWidth(fraction = 0.92f)
                    .height(174.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                val data = tempData[page]

                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(data),
                    contentDescription = "AAA",
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 21.dp, bottom = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(
                        if (pagerStateEvent.currentPage == 0) 20.dp else 4.dp,
                        4.dp
                    )
                    .background(
                        if (pagerStateEvent.currentPage == 0) Color3 else Color(
                            0xFFC4C4C4
                        ), RoundedCornerShape(100)
                    )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(
                        if (pagerStateEvent.currentPage == 1) 20.dp else 4.dp,
                        4.dp
                    )
                    .background(
                        if (pagerStateEvent.currentPage == 1) Color3 else Color(
                            0xFFC4C4C4
                        ), RoundedCornerShape(100)
                    )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(
                        if (pagerStateEvent.currentPage == 2) 20.dp else 4.dp,
                        4.dp
                    )
                    .background(
                        if (pagerStateEvent.currentPage == 2) Color3 else Color(
                            0xFFC4C4C4
                        ), RoundedCornerShape(100)
                    )
            )
        }
    }
}