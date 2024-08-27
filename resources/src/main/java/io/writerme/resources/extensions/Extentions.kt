package io.writerme.resources.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.view.WindowCompat
import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.core.common.GlobalConstants.AppLink.linkTag
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_1
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_25
import io.writerme.resources.common.Dimens.GRID_40
import io.writerme.resources.themes.WriterMeTheme

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableWithoutRipple(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { onClick() },
        role = role,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableRipple(
    enabled: Boolean = true,
    isBounded: Boolean = false,
    onClickLabel: String? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["onClick"] = onClick
    }
) {
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { onClick() },
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = isBounded)
    )
}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentFraction: Float
    get() {
        val fraction = bottomSheetState.progress
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue
        val expandedFraction = 1f
        val collapsedFraction = 0f
        return when {
            currentValue == BottomSheetValue.Collapsed &&
                targetValue == BottomSheetValue.Collapsed -> collapsedFraction

            currentValue == BottomSheetValue.Expanded &&
                targetValue == BottomSheetValue.Expanded -> expandedFraction

            currentValue == BottomSheetValue.Collapsed &&
                targetValue == BottomSheetValue.Expanded -> fraction

            else -> expandedFraction - fraction
        }
    }

@Composable
fun setBarsColor(color: Color) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
            window.navigationBarColor = color.toArgb()

            if (color.toArgb() < 0xFFA0ADBF) {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = true
                    isAppearanceLightNavigationBars = true
                }
            } else {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = false
                    isAppearanceLightNavigationBars = false
                }
            }
        }
    }
}

/**
 * Important: styles should be listed in the order they are met in the string!
 */
@Composable
fun annotate(
    main: String,
    mainStyle: TextStyle,
    vararg styles: Triple<String, TextStyle, String>
): AnnotatedString {
    var previous = ZERO
    val last = styles.size - VALUE_1

    return buildAnnotatedString {
        styles.forEachIndexed { index, triple ->
            val isLast = index == last
            val firstOccurrence = main.indexOf(triple.first)

            if (firstOccurrence != -VALUE_1) {
                if (firstOccurrence != ZERO) {
                    withStyle(style = mainStyle.toSpanStyle()) {
                        append(
                            main.substring(previous, firstOccurrence)
                        )
                    }
                }

                pushStringAnnotation(
                    tag = linkTag,
                    annotation = triple.third
                )
                withStyle(style = triple.second.toSpanStyle()) {
                    append(triple.first)
                }
                pop()

                previous = firstOccurrence + triple.first.length

                if (isLast && previous != last) {
                    withStyle(style = mainStyle.toSpanStyle()) {
                        append(
                            main.substring(previous)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeFilterTab.displayName(): String {
    return when (this) {
        HomeFilterTab.All -> {
            stringResource(id = R.string.all)
        }

        HomeFilterTab.Important -> {
            stringResource(id = R.string.important)
        }
    }
}

@Composable
fun Modifier.textFieldBackground() =
    this
        .clip(RoundedCornerShape(GRID_25))
        .border(
            GRID_1,
            WriterMeTheme.colors.strokeLight,
            RoundedCornerShape(GRID_25)
        )
        .background(WriterMeTheme.colors.fieldDark)
        .padding(horizontal = GRID_16, vertical = GRID_0)
        .height(GRID_40)

fun ClipboardManager.copyComponentContent(
    component: ComponentViewData,
    context: Context
) {
    val text = when (component.type) {
        ComponentType.Text, ComponentType.Checkbox, ComponentType.Task -> component.content

        ComponentType.Voice, ComponentType.Link,
        ComponentType.Video, ComponentType.Image -> component.url
    }

    this.setText(AnnotatedString(text))

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        Toast.makeText(context, context.resources.getString(R.string.copied), Toast.LENGTH_SHORT)
            .show()
    }
}
