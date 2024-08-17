package io.writerme.resources.themes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

object SharemyTheme {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppColors provides basePalette,
        LocalAppTypography provides baseTypography,
        content = content
    )
}

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("No font provided")
}
