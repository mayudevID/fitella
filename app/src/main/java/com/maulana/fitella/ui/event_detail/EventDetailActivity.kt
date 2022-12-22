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
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        var startAnim by remember { mutableStateOf(false) }
        var whiteSizeState by remember { mutableStateOf(0.dp) }
        val whiteSize by animateDpAsState(
            targetValue = whiteSizeState, animationSpec = tween(
                durationMillis = 500,
            )
        )

        BackHandler(
            enabled = true,
            onBack = {
                whiteSizeState -= 560.dp
                Handler(Looper.getMainLooper()).postDelayed({ finish() }, 250)
            }
        )
        Box(Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(317.11.dp),
                painter = painterResource(R.drawable.temp_event_detail),
                contentScale = ContentScale.Crop,
                contentDescription = "Event Image"
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(570.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color2
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 31.dp, vertical = 7.dp),
                ) {
                    Image(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(R.drawable.temp_profile_event),
                        contentDescription = "Profile Event"
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
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
                            Spacer(modifier = Modifier.width(7.dp))
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(whiteSize)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color.White
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 30.dp, top = 23.dp)
                ) {
                    //Spacer(modifier = Modifier.height(23.dp))
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
                }
            }
        }

        if (!startAnim) {
            whiteSizeState += 506.dp
            startAnim = true
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

