package com.maulana.fitella.ui.app_menu.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins
import kotlinx.coroutines.launch

val tempPost = listOf(
    R.drawable.temp_post_1,
    R.drawable.temp_post_2,
    R.drawable.temp_post_3,
    R.drawable.temp_post_2,
    R.drawable.temp_post_3,
    R.drawable.temp_post_1,
    R.drawable.temp_post_2,
    R.drawable.temp_post_1,
    R.drawable.temp_post_3,
    R.drawable.temp_post_2,
    R.drawable.temp_post_3,
    R.drawable.temp_post_1,
    R.drawable.temp_post_2,
    R.drawable.temp_post_1,
)

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun PhotoActivityScreen() {
    val pagerState = rememberPagerState(initialPage = 0)

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Tabs(pagerState = pagerState)
        Spacer(modifier = Modifier.height(14.dp))
        TabsContent(pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Photo",
        "Activity"
    )

    val scope = rememberCoroutineScope()
    TabRow(
        modifier = Modifier
            .padding(horizontal = 109.dp)
            .height(27.dp),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .height(2.dp)
                    .padding(horizontal = 33.dp)
                    .background(color = Color2, shape = RoundedCornerShape(8.dp))
            )
        },
    ) {

        list.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier.width(1.dp),
                text = {
                    Text(
                        list[index],
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = if (pagerState.currentPage == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState, count = 2) { page ->
        when (page) {
            0 -> PhotoTab()
            1 -> ActivityTab()
        }
    }
}

@Composable
fun PhotoTab() {
    LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(3), contentPadding = PaddingValues(1.dp)) {
        items(tempPost.size) {
            Card(
                modifier = Modifier.size(124.dp).clickable {  },
                shape = RoundedCornerShape(3.dp),
                elevation = 0.dp
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(3.dp)),
                    painter = painterResource(tempPost[it]),
                    contentDescription = "Post Users",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun ActivityTab() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(3) {
            Card(
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 6.dp),
                backgroundColor = Color(0xFFF2F2F2),
                elevation = 0.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 17.48.dp, vertical = 6.dp)) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = Poppins,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            ) {
                                append("kahfa.gaming ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = Poppins,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )
                            ) {
                                append("eats 600 calories for his dinner! - Orange Fit Restaurant.")
                            }
                        }
                    )
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = "Just Now", style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 6.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black
                        )
                    )
                }

            }
        }
    }
}