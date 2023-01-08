package com.maulana.fitella.ui.app_menu.profile.sub_menu

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maulana.fitella.R
import com.maulana.fitella.theme.Color2
import com.maulana.fitella.theme.Poppins
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun ReportScreen(
    setShowDialog: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = "Report", style = TextStyle(
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
        Card(modifier = Modifier.height(223.dp).fillMaxWidth()) {
            LineChart(
                data = listOf(
                    Pair("Sun", 70.0),
                    Pair("Mon", 70.0),
                    Pair("Tue", 55.0),
                    Pair("Wed", 55.0),
                    Pair("Thu", 40.0),
                    Pair("Fri", 37.0),
                    Pair("Sat", 35.0),
                ), modifier = Modifier
                    .fillMaxSize()
                    .align(CenterHorizontally)
            )
        }
    }
}

@Composable
fun LineChart(
    data: List<Pair<String, Double>> = emptyList(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val spacing = 100f
    val graphColor = Color.Red
//    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = remember { (data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0 }
    val lowerValue = remember { (data.minOfOrNull { it.second }?.toInt() ?: 0) }
    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 10.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size
        (data.indices step 1).forEach { i ->
            val hour = data[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append(hour)
                        }
                    }.text,
                    spacing + i * spacePerHour,
                    size.height,
                    textPaint
                )
            }
        }

//        val priceStep = (upperValue - lowerValue) / 5f
//        (0..4).forEach { i ->
//            drawContext.canvas.nativeCanvas.apply {
//                drawText(
//                    round(lowerValue + priceStep * i).toString(),
//                    30f,
//                    size.height - spacing - i * size.height / 5f,
//                    textPaint
//                )
//            }
//        }

        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val info = data[i]
                val ratio = (info.second - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (ratio * height).toFloat()

                if (i == 0) {
                    moveTo(x1, y1)
                }
                lineTo(x1, y1)
            }
        }

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

//        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
//            lineTo(size.width - spacePerHour, size.height - spacing)
//            lineTo(spacing, size.height - spacing)
//            close()
//        }

//        drawPath(
//            path = fillPath,
//            brush = Brush.verticalGradient(
//                colors = listOf(
//                    transparentGraphColor,
//                    Color.Transparent
//                ),
//                endY = size.height - spacing
//            )
//        )

    }
}


@Preview(showBackground = true)
@Composable
fun RsPreview() {
    ReportScreen(setShowDialog = {})
}