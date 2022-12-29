package com.maulana.fitella.ui.event_detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Color5
import com.maulana.fitella.theme.FitellaTheme
import com.maulana.fitella.theme.Poppins

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FitellaTheme(this, darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {
                    ContentUi()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ContentUi() {
        var whiteSize by remember { mutableStateOf(0f) }
        var orangeSize by remember { mutableStateOf(0.6f) }
        val whiteSizeState by animateFloatAsState(
            targetValue = whiteSize, animationSpec = tween(
                durationMillis = 450,
            )
        )
        val orangeSizeState by animateFloatAsState(
            targetValue = orangeSize, animationSpec = tween(
                durationMillis = 450
            )
        )

        val backFromPage: () -> Unit = {
            whiteSize = 0f
            orangeSize = 0.6f
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    finish()
                }, 250
            )
        }

        LaunchedEffect(true) {
            orangeSize = 0.6773399f
            whiteSize = 0.5985222f
        }

        BackHandler(
            enabled = true,
            onBack = backFromPage
        )
        Box(Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f),
                painter = painterResource(R.drawable.temp_event_detail),
                contentScale = ContentScale.Crop,
                contentDescription = "Event Image"
            )
            Image(
                modifier = Modifier
                    .padding(
                        top = 38.dp, start = 30.dp
                    )
                    .size(7.dp, 14.dp)
                    .clickable(onClick = backFromPage),
                imageVector = ImageVector.vectorResource(R.drawable.back),
                contentDescription = "Back"
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(orangeSizeState)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color2
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.11636364f)
                            .padding(horizontal = 31.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(0.75f)
                                .fillMaxWidth(0.15f),
                            painter = painterResource(R.drawable.temp_profile_event),
                            contentDescription = "Profile Event"
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        Column(
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Sport Universe",
                                style = TextStyle(
                                    fontFamily = Poppins,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.star_event),
                                    contentDescription = "Star Event"
                                )
                                Text(
                                    text = "(4.8)", style = TextStyle(
                                        fontFamily = Poppins,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Light,
                                        color = Color.White,
                                        baselineShift = BaselineShift(-0.21f)
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(Modifier.padding(8.dp)) {
                            Image(
                                ImageVector.vectorResource(R.drawable.share_event),
                                modifier = Modifier.size(12.dp),
                                contentDescription = "Share Button"
                            )
                            Spacer(modifier = Modifier.width(13.dp))
                            Image(
                                ImageVector.vectorResource(R.drawable.save_event),
                                modifier = Modifier.size(12.dp),
                                contentDescription = "Share Button"
                            )
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(whiteSizeState)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color.White
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 30.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(23.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Detail Event",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    TitleAndDescription(
                        textA = "Name of the event:",
                        textB = "Sunday Morning Run Together"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TitleAndDescription(
                        textA = "Description:",
                        textB = "Sunday Morning Run Together is an event for running together at the stadion and the goal is to spread awareness of the healthy life. You can visit the games stand and if you can passed the game, you will receive the gift."
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TitleAndDescription(
                        textA = "Location:",
                        textB = "Jalan Pintu Satu Senayan, Gelora, Tanah Abang, Kota Jakarta Pusat, DKI Jakarta. Nomor telepon: (021) 5734070, kode pos: 10270."
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TitleAndDescription(textA = "Type of Sport:", textB = "Run")
                    Spacer(modifier = Modifier.height(4.dp))
                    TitleAndDescription(textA = "Capacity:", textB = "800 people")
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(Color5),
                            shape = RoundedCornerShape(100),
                            modifier = Modifier
                                .size(width = 218.dp, height = 40.dp)
                                .align(Alignment.Center)
                                .shadow(
                                    shape = RoundedCornerShape(100),
                                    elevation = 7.dp,
                                    ambientColor = Color5,
                                    spotColor = Color5
                                ),
                            elevation = ButtonDefaults.elevation(0.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    ImageVector.vectorResource(R.drawable.next),
                                    contentDescription = "View Web"
                                )
                                Spacer(modifier = Modifier.width(7.dp))
                                Text(
                                    text = "View on Website",
                                    style = TextStyle(
                                        fontFamily = Poppins,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        baselineShift = BaselineShift(-0.21f)
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(23.dp))
                }
            }
        }
    }
}

@Composable
fun TitleAndDescription(textA: String, textB: String) {
    Text(
        text = textA, style = TextStyle(
            fontFamily = Poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color2,
            textAlign = TextAlign.Start,
        )
    )
    Spacer(modifier = Modifier.height(4.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = Color(0xFFF7F7F7),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp),
            text = textB,
            style = TextStyle(
                fontFamily = Poppins,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black,
                textAlign = TextAlign.Justify,
            )
        )
    }
}

