package io.writerme.resources.themes

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.writerme.resources.R

val FONT_0_2 = 0.2.sp
val FONT_1 = 1.sp
val FONT_2 = 2.sp
val FONT_5 = 5.sp
val FONT_7 = 7.sp
val FONT_10 = 10.sp
val FONT_11 = 11.sp
val FONT_12 = 12.sp
val FONT_13 = 13.sp
val FONT_14 = 14.sp
val FONT_15 = 15.sp
val FONT_16 = 16.sp
val FONT_17 = 17.sp
val FONT_18 = 18.sp
val FONT_20 = 20.sp
val FONT_21 = 21.sp
val FONT_22 = 22.sp
val FONT_24 = 24.sp
val FONT_26 = 26.sp
val FONT_28 = 28.sp
val FONT_30 = 30.sp
val FONT_32 = 32.sp
val FONT_34 = 34.sp
val FONT_36 = 36.sp
val FONT_39 = 39.sp
val FONT_40 = 40.sp
val FONT_42 = 42.sp
val FONT_48 = 48.sp
val FONT_50 = 50.sp
val FONT_56 = 56.sp
val FONT_60 = 60.sp

val ubuntuFontFamily = FontFamily(
    Font(R.font.ubuntu_light, FontWeight.Light),
    Font(R.font.ubuntu_regular, FontWeight.Normal),
    Font(R.font.ubuntu_medium, FontWeight.Medium),
    Font(R.font.ubuntu_bold, FontWeight.Bold)
)

data class AppTypography(
    val welcomeTitle: TextStyle,
    val button: TextStyle,
    val editText: TextStyle
)

val baseTypography = AppTypography(
    welcomeTitle = TextStyle(
        color = basePalette.primaryBlue,
        fontSize = FONT_48,
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    button = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontSize = FONT_16,
        lineHeight = FONT_24,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    editText = TextStyle(
        fontFamily = ubuntuFontFamily,
        color = basePalette.primaryText,
        fontSize = FONT_16,
        lineHeight = FONT_22,
        fontWeight = FontWeight.Normal
    )
)
