package com.thelis.test3

import android.R.color
import android.content.Intent
import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thelis.test3.ui.ThemeData
import com.thelis.test3.ui.ThemeMode
import com.thelis.test3.ui.theme.JetPackTheme
import com.thelis.test3.ui.theme.colorBoard
import com.thelis.test3.ui.theme.monrepoFamily


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThemeModeValue()
        }
    }
}


@Composable
fun ThemeModeValue() {
    val context = LocalContext.current
    val mode = remember { mutableStateOf(ThemeData(ThemeMode.Light, false)) }
    JetPackTheme(darkTheme = mode.value.value,
        content = {
            Scaffold(content = {
                Column(content = {
                    Row(Modifier
                        .padding(start = 40.dp, top = 40.dp)

                    ) {

                        Switch(
                            modifier = Modifier.scale(scaleX = 2.5f, scaleY = 2.5f),
                            checked = mode.value.title == ThemeMode.Dark,
                            onCheckedChange = {
                                if (mode.value.title == ThemeMode.Dark) {
                                    mode.value = ThemeData(ThemeMode.Light, false)
                                } else {
                                    mode.value = ThemeData(ThemeMode.Dark, true)
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.size(100.dp))
                    Column(modifier = Modifier
                        .drawBehind {
                            val linechart =
                                listOf<Long>(50,
                                    100,
                                    150,
                                    200,
                                    250,
                                    300,
                                    350,
                                    400,
                                    450,
                                    500,
                                    499,
                                    497,
                                    490,
                                    480,
                                    440,
                                    400,
                                    300,
                                    200,
                                    -100,
                                    100)
                            val distance = size.width / (linechart.size + 1).toInt()
                            var currentX: Int = 0
                            val maxValue = linechart.maxOrNull()
                            val points = mutableListOf<Point>()
                            linechart.forEachIndexed { index, data ->
                                if (linechart.size >= index + 2) {
                                    val y0 = (maxValue?.minus(data)
                                        ?.times((size.height / maxValue)))
                                    val x0: Float = currentX + distance
                                    points.add(Point(x0.toInt(), y0!!.toInt()))
                                    currentX += distance.toInt()
                                }

                            }
                            for (i in 0 until points.size - 1) {
                                drawLine(
                                    start = Offset(points[i].x.toFloat(),
                                        points[i].y.toFloat()),
                                    end = Offset(points[i + 1].x.toFloat(),
                                        points[i + 1].y.toFloat()),
                                    color = Color(40, 193, 218),
                                    strokeWidth = Stroke.DefaultMiter.plus(5f),
                                    blendMode = BlendMode.Hardlight,
                                    pathEffect = PathEffect.cornerPathEffect(10f)

                                )
                            }


                        }
                    )

                    {
                        Text(
                            text = "Find all apps",

                            modifier = Modifier
                                .padding(start = 100.dp)
                                .rotate(-6f),
                            fontSize = 34.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = monrepoFamily,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.size(30.dp))
                        Text(
                            text = "On your phone",

                            modifier = Modifier
                                .padding(start = 100.dp)
                                .rotate(10f),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = monrepoFamily,
                            fontWeight = FontWeight.Light,
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                })

                Box(modifier = Modifier
                    .padding(start = 75.dp, top = 450.dp)


                ) {
                    ExtendedFloatingActionButton(
                        icon = {
                            Icon(Icons.Filled.Search, "Search button", tint = Color.White,
                                modifier = Modifier.padding(start = 20.dp, bottom = 30.dp))
                        },
                        text = {
                            Text(
                                "SCAN",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontFamily = monrepoFamily,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(
                                        start = 10.dp,
                                        top = 60.dp,
                                        end = 60.dp,
                                        bottom = 60.dp),
                                textAlign = TextAlign.Center,
                            )
                        },
                        onClick = {
                            val intent = Intent(context, CardActivity::class.java)
                            context.startActivity(intent)
                        },
                        elevation = FloatingActionButtonDefaults.elevation(8.dp),
                        contentColor = MaterialTheme.colors.onBackground,
                        backgroundColor = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .shadow(300.dp, shape = CircleShape, clip = true)
                            .border(5.dp, color = Color(colorBoard), shape = CircleShape),
                        shape = CircleShape
                    )
                }
            })

        }
    )


}

