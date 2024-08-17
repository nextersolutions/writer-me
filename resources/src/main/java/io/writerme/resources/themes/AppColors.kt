package io.writerme.resources.themes

import androidx.compose.ui.graphics.Color

data class AppColors(
    val primaryBlue: Color,
    val primaryBlueDisabled: Color,
    val primaryBackground: Color
)

val basePalette = AppColors(
    primaryBlue = Color(color = 0xFF012D6A),
    primaryBlueDisabled = Color(color = 0xFFA0ADBF),
    primaryBackground = Color.White
)
