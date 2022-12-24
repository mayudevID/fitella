package com.maulana.fitella.ui.app_menu.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins
import kotlinx.coroutines.launch

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun PhotoActivityScreen() {
    val pagerState = rememberPagerState(initialPage = 0)

    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Tabs(pagerState = pagerState)
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
    HorizontalPager(state = pagerState, count = 2) { page ->
        when (page) {
            0 -> PhotoTab()
            1 -> ActivityTab()
        }
    }
}

@Composable
fun PhotoTab() {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {

    }
}

@Composable
fun ActivityTab() {

}