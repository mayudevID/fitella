package com.maulana.fitella.ui.app_menu.event

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.animation.AnimationUtils.lerp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color3
import com.maulana.fitella.theme.Color5
import com.maulana.fitella.theme.Poppins
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

val tempData = listOf(
    R.drawable.temp_event_1,
    R.drawable.temp_event_2,
    R.drawable.temp_event_3
)

val gridTempData = listOf(
    R.drawable.event1,
    R.drawable.event2,
    R.drawable.event3,
    R.drawable.event4,
    R.drawable.event5,
    R.drawable.event6,
    R.drawable.event7,
    R.drawable.event8,
    R.drawable.event9,
)

@Preview(showBackground = true)
@Composable
fun EventScreen() {
    Surface {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 10.dp)
            ) {
                Column {
                    SliderEventTop()
                    Spacer(modifier = Modifier.height(11.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.49.dp)
                            .padding(horizontal = 14.dp),
                        elevation = 0.dp,
                        backgroundColor = Color(0xFFF7F7F7),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 13.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                ImageVector.vectorResource(R.drawable.clarity_search_line),
                                modifier = Modifier.size(10.dp),
                                contentDescription = "Search Icon"
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            BoxSearch()
                        }
                    }
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = 15.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(21.dp),
                horizontalArrangement = Arrangement.spacedBy(21.dp)
            ) {
                items(9) { pos ->
                    Card(Modifier.fillMaxWidth(), elevation = 0.dp) {
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
                                text = "Ticket prices start from IDR 50.000, domiciled in Bali."
                            ,
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

@Composable
fun BoxSearch() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Box(modifier = Modifier.fillMaxWidth()) {
        if (searchQuery.text.isEmpty() || searchQuery.text == "") {
            Text(
                text = "Search",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = Poppins,
                    color = Color(0xFF858585),
                    fontStyle = FontStyle.Italic,
                    baselineShift = BaselineShift(-0.21f)
                ),
            )
        }
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchQuery,
            cursorBrush = Brush.verticalGradient(
                0.00f to Color.Transparent,
                0.20f to Color5,
                0.80f to Color5,
                1.00f to Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.Light,
                fontFamily = Poppins,
                color = Color.Black,
            ),
            onValueChange = {
                if (it.text.length <= 50) searchQuery = it
            },
        )
    }
}