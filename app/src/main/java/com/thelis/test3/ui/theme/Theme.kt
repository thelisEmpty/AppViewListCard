package com.thelis.test3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.thelis.test3.R

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
val light = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColorForLight,
    secondaryVariant = SecondaryColorForLight,
    background = BackgroundColorForLight,
    surface = SurfaceColorForLight,
    error = ErrorColor,
    onPrimary = WhiteColor,
    onSecondary = WhiteColor,
    onBackground = BlackColor,
    onSurface = BlackColor,
    onError = ErrorColor
)
val dark = darkColors(
    background = BackgroundColorForDark,
    surface = SurfaceColorForDark,
    primary = PrimaryColor,
    secondary = SecondaryColorForDark,
    onBackground = OnBackgroundColor,
    onSurface = WhiteColor,
    onPrimary = WhiteColor,
    onSecondary = WhiteColor,
    primaryVariant = PrimaryVariantColor,
    secondaryVariant = SecondaryColorForDark,
    onError = ErrorColor,
    error = ErrorColor,
)

@Composable
fun JetPackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) dark else light,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val monrepoFamily = FontFamily(
    Font(R.font.manropeextralight, FontWeight.Light),
    Font(R.font.manropebold, FontWeight.Normal),
    Font(R.font.manropeextrabold, FontWeight.Bold),
)

@Composable
fun Test3Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}