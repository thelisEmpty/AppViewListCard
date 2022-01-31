package com.thelis.test3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter.formatFileSize
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.thelis.test3.model.AppListModel
import com.thelis.test3.ui.ThemeData
import com.thelis.test3.ui.ThemeMode
import com.thelis.test3.ui.theme.JetPackTheme
import com.thelis.test3.ui.theme.monrepoFamily
import java.io.File
import java.util.*


var installedAppsList: ArrayList<AppListModel> = ArrayList()
var selectedApp: AppListModel? = null


class CardActivity : ComponentActivity() {
    val index: Int? = null

    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackTheme {

                getInstalledApps()
                AppCardView()


            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("QueryPermissionsNeeded", "UsableSpace")
    fun getInstalledApps(): ArrayList<AppListModel> {
        installedAppsList.clear()
        val packs = packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (!isSystemPackage(p)) {
                val appName = p.applicationInfo.loadLabel(packageManager).toString()
                val icon = p.applicationInfo.loadIcon(packageManager).toBitmap()
                val dateInstalled = p.firstInstallTime
                val version = p.applicationInfo.minSdkVersion

                /*val file = File(applicationContext.packageManager.getApplicationInfo(
                    applicationContext.packageName, 0).sourceDir)*/

                val file = File(p.applicationInfo.sourceDir)
                val size = formatFileSize(this, file.length())

                Log.d("size", size.toString())
                installedAppsList.add(AppListModel(appName, icon, size, dateInstalled, version))

            }

        }
        installedAppsList.sortBy { it.getName().capitalized() }
        return installedAppsList
    }

    private fun String.capitalized(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    @ExperimentalFoundationApi
    @Composable
    fun AppCardView() {
        val mode = remember { mutableStateOf(ThemeData(ThemeMode.Dark, true)) }
        JetPackTheme(darkTheme = mode.value.value,
            content = {
                Scaffold(content = {
                    Column(content = {

                        Row(Modifier
                            .padding(start = 40.dp, top = 40.dp)
                            .background(color = Color.Transparent)) {
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
                            Box(modifier = Modifier
                                .padding(start = 25.dp, top = 30.dp)) {
                                Text(
                                    text = "SCAN RESULT",
                                    modifier = Modifier
                                        .padding(start = 100.dp),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = monrepoFamily,
                                    fontWeight = FontWeight.Bold,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.size(15.dp))
                        //  getInstalledApps()


                        LazyVerticalGrid(
                            cells = GridCells.Fixed(2),
                            modifier = Modifier
                                .padding(10.dp)) {
                            installedAppsList.forEach {
                                item {
                                    PlaceCard(
                                        ImgRes = it.getIcon(),
                                        AppName = it.getName(),
                                        size = it.getSize(),
                                        it.getInstallationDate(),
                                        it.getSdkVersion())


                                }
                            }
                        }
                    })
                })
            }
        )
    }
}


@Composable
private fun PlaceCard(
    ImgRes: Bitmap,
    AppName: String,
    size: String?,
    installedDate: Long?,
    sdk: Int?,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
            .width(150.dp)
            .height(150.dp)
            .clickable {
                selectedApp = AppListModel(icon = ImgRes,
                    name = AppName,
                    size = size!!,
                    installedDate = installedDate!!,
                    sdkVer = sdk!!)
                val intent = Intent(context, PersonalCardActivity::class.java)
                context.startActivity(intent)

            },
        elevation = 8.dp,
        shape = RoundedCornerShape(24.dp),

        ) {
        Box(modifier = Modifier
            .height(130.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    bitmap = ImgRes.asImageBitmap(),
                    modifier = Modifier
                        .padding(top = 35.dp, start = 45.dp)
                        .size(
                            64.dp, 64.dp,
                        ),
                    contentDescription = null)
                Text(text = AppName.take(9),
                    modifier = Modifier
                        .padding(start = 45.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontFamily = monrepoFamily
                    ),
                    fontSize = 17.sp
                )
            }

        }

    }
}





