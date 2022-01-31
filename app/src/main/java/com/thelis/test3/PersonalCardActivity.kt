package com.thelis.test3

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thelis.test3.ui.ThemeData
import com.thelis.test3.ui.ThemeMode
import com.thelis.test3.ui.theme.JetPackTheme
import com.thelis.test3.ui.theme.monrepoFamily
import java.text.SimpleDateFormat


class PersonalCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackTheme {


                selectedApp?.let { it ->
                    PersonalCard(name = it.getName(),
                        image = it.getIcon(),
                        size = it.getSize(),
                        targetSdkVersion = it.getSdkVersion(),
                        installationDate = it.getInstallationDate())
                }


            }
        }
    }
}


@SuppressLint("SimpleDateFormat")
@Composable
fun PersonalCard(
    name: String,
    image: Bitmap,
    size: String,
    targetSdkVersion: Int,
    installationDate: Long,
) {

    val mode = remember { mutableStateOf(ThemeData(ThemeMode.Light, false)) }
    JetPackTheme(darkTheme = mode.value.value,
        content = {
            Scaffold(content = {
                Row(content = {

                    Column(Modifier
                        .padding(top = 40.dp)
                    ) {
                        Switch(
                            modifier = Modifier
                                .scale(scaleX = 2.5f, scaleY = 2.5f)
                                .padding(start = 40.dp),
                            checked = mode.value.title == ThemeMode.Dark,
                            onCheckedChange = {
                                if (mode.value.title == ThemeMode.Dark) {
                                    mode.value = ThemeData(ThemeMode.Light, false)
                                } else {
                                    mode.value = ThemeData(ThemeMode.Dark, true)
                                }
                            }
                        )
                        val paddingModifier = Modifier.padding(10.dp)
                        Spacer(modifier = Modifier.size(15.dp))

                        Card(
                            shape = RoundedCornerShape(24.dp),
                            elevation = 10.dp,
                            modifier = Modifier
                                .padding(top = 30.dp, end = 28.dp, start = 28.dp)
                                .width(358.dp)
                                .height(517.dp)
                                //.background(MaterialTheme.colors.onBackground)
                                .shadow(15.dp, shape = RoundedCornerShape(15.dp), clip = true),


                            ) {
                            Column {

                                Image(
                                    bitmap = image.asImageBitmap(),
                                    contentDescription = "Image",
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(start = 115.dp, top = 72.dp)
                                        .size(100.dp, 100.dp),
                                )
                                Text(
                                    text = name, Modifier.padding(start = 35.dp, top = 24.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = monrepoFamily),
                                    fontSize = 32.sp,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.size(35.dp))


                                Text(text = "Size :${size}"/*formatAsFileSize}".uppercase(
                                    Locale.getDefault())*/, modifier = paddingModifier,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = monrepoFamily),
                                    fontSize = 20.sp)
                                Divider(color = MaterialTheme.colors.onBackground,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(start = 10.dp,
                                        end = 15.dp,
                                        bottom = 17.dp,
                                        top = 17.dp))
                                Text(text = "TargetSdkVersion: $targetSdkVersion",
                                    modifier = paddingModifier,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = monrepoFamily),
                                    fontSize = 20.sp)
                                Divider(color = MaterialTheme.colors.onBackground,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(start = 10.dp,
                                        end = 17.dp,
                                        bottom = 17.dp))


                                val dateFotmat = SimpleDateFormat("dd.MM.yyyy")
                                val installTime = dateFotmat.format(installationDate)


                                Text(text = "Installation date:${installTime}",
                                    modifier = paddingModifier,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = monrepoFamily),
                                    fontSize = 20.sp)

                            }
                        }
                        val context = LocalContext.current
                        Button(
                            onClick = {
                                val intent = Intent(context, CardActivity::class.java)
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(23.dp),
                            modifier = Modifier
                                .width(550.dp)
                                .padding(start = 40.dp, top = 30.dp, end = 40.dp),
                            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.background,
                                backgroundColor = MaterialTheme.colors.onBackground)

                        ) {
                            Text(
                                text = "BACK",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = monrepoFamily),
                                fontSize = 24.sp,

                                )
                        }

                    }
                })
            })

        })
}


