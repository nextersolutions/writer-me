package io.writerme.resources.themes

import androidx.compose.ui.graphics.Color

data class AppColors(
    val primaryBlue: Color,
    val primaryBlueDisabled: Color,
    val primaryBackground: Color,
    val cardBackground: Color,
    val light: Color,
    val strokeLight: Color,
    val lightGrey: Color,
    val dialogBackground: Color,
    val dropdownBackground: Color,
    val fieldDark: Color,
    val backgroundGrey: Color,
    val lightTransparent: Color,
    val primaryText: Color
)

val basePalette = AppColors(
    primaryBlue = Color(color = 0xFF012D6A),
    primaryBlueDisabled = Color(color = 0xFFA0ADBF),
    primaryBackground = Color.White,
    cardBackground = Color(0xA6292D32),
    light = Color(0xFFF5F5F5),
    strokeLight = Color(0x808C8989),
    lightGrey = Color(0xA6D9D9D9),
    dialogBackground = Color(0xD9292D32),
    dropdownBackground = Color(0xF2292D32),
    fieldDark = Color(0x40000000),
    backgroundGrey = Color(0xA66B6A6A),
    lightTransparent = Color(0x26D9D9D9),
    primaryText = Color(0xACFFFFFF)
)
