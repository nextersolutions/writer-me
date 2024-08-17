package io.writerme.resources.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import io.writerme.core.common.GlobalConstants.animationDuration
import io.writerme.core.common.GlobalConstants.animationDurationLong
import io.writerme.resources.common.Dimens.GRID_40
import io.writerme.resources.common.Dimens.WEIGHT_03

object VisibilityWidgets {

    @Composable
    fun VisibilityFade(
        isVisible: Boolean,
        enterDuration: Int = animationDuration,
        exitDuration: Int = animationDuration,
        modifier: Modifier = Modifier,
        content: @Composable AnimatedVisibilityScope.() -> Unit
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(durationMillis = enterDuration)),
            exit = fadeOut(tween(durationMillis = exitDuration)),
            modifier = modifier,
            content = content
        )
    }

    @Composable
    fun VisibilityFadeFromTop(
        isVisible: Boolean,
        content: @Composable AnimatedVisibilityScope.() -> Unit
    ) {
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically {
                with(density) { -GRID_40.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = WEIGHT_03
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut(),
            content = content
        )
    }

    @Composable
    fun VisibilityVertically(
        isVisible: Boolean,
        enter: EnterTransition = expandVertically(),
        exit: ExitTransition = shrinkVertically(),
        content: @Composable AnimatedVisibilityScope.() -> Unit
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = enter,
            exit = exit,
            content = content
        )
    }

    @Composable
    fun VisibilityScale(
        isVisible: Boolean,
        content: @Composable AnimatedVisibilityScope.() -> Unit
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = scaleIn(tween(durationMillis = animationDurationLong, easing = LinearOutSlowInEasing)),
            exit = scaleOut(tween(durationMillis = animationDurationLong, easing = LinearOutSlowInEasing)),
            content = content
        )
    }
}
