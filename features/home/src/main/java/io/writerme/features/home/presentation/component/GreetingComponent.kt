package io.writerme.features.home.presentation.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.SECOND
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_120
import io.writerme.resources.common.Dimens.GRID_2
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.common.Dimens.GRID_230
import io.writerme.resources.common.Dimens.GRID_24
import io.writerme.resources.common.Dimens.GRID_34
import io.writerme.resources.common.Dimens.GRID_355
import io.writerme.resources.common.Dimens.GRID_400
import io.writerme.resources.common.Dimens.GRID_55
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.FONT_40
import io.writerme.resources.themes.WriterMeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GreetingComponent(
    proceedToNextScreen: () -> Unit
) {
    val writerMe = stringResource(id = R.string.app_name)
    val ideas = stringResource(id = R.string.ideas)

    var hasImageAnimationStarted by remember {
        mutableStateOf(false)
    }

    var hasTopArcAnimationStarted by remember {
        mutableStateOf(false)
    }

    var hasBottomArcAnimationStarted by remember {
        mutableStateOf(false)
    }

    var writerText by remember {
        mutableStateOf(EMPTY)
    }

    var ideasText by remember {
        mutableStateOf(EMPTY)
    }

    val topAngle: Float by animateFloatAsState(
        targetValue = if (hasTopArcAnimationStarted) -160f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "topAngle"
    )

    val bottomAngle: Float by animateFloatAsState(
        targetValue = if (hasBottomArcAnimationStarted) 90f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "bottomAngle"
    )

    LaunchedEffect(
        key1 = null
    ) {
        launch {
            delay(500)
            hasImageAnimationStarted = true

            delay(1500)
            hasTopArcAnimationStarted = true

            delay(700)
            hasBottomArcAnimationStarted = true

            delay(5000)
            proceedToNextScreen()
        }

        launch {
            delay(2000)

            writerMe.forEachIndexed { charIndex, _ ->
                writerText = writerMe.substring(
                    startIndex = ZERO,
                    endIndex = charIndex + VALUE_1
                )
                delay(120)
            }

            delay(800)

            ideas.forEachIndexed { charIndex, _ ->
                ideasText = ideas.substring(
                    startIndex = ZERO,
                    endIndex = charIndex + VALUE_1
                )
                delay(90)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = stringResource(id = R.string.background_image),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val shape = RoundedCornerShape(
                topStart = GRID_230,
                topEnd = GRID_230,
                bottomEnd = GRID_230,
                bottomStart = GRID_230
            )

            Box(
                modifier = Modifier
                    .offset(x = (-80).dp, y = GRID_55)
                    .size(GRID_400)
            ) {
                val color = WriterMeTheme.colors.strokeLight

                this@Column.AnimatedVisibility(
                    visible = hasImageAnimationStarted,
                    enter = slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = SECOND.toInt(),
                            easing = LinearEasing
                        )
                    ) + scaleIn(
                        animationSpec = tween(
                            durationMillis = SECOND.toInt(),
                            easing = LinearEasing
                        )
                    ),
                    exit = slideOutHorizontally() + scaleOut(),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .shadow(GRID_20, shape),
                        shape = shape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.greeting),
                            contentDescription = stringResource(id = R.string.image),
                            modifier = Modifier
                                .size(GRID_355),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Canvas(
                    modifier = Modifier.size(GRID_400)
                ) {
                    drawArc(
                        color = color,
                        startAngle = 10f,
                        sweepAngle = topAngle,
                        useCenter = false,
                        style = Stroke(width = GRID_2.toPx())
                    )

                    drawArc(
                        color = color,
                        startAngle = 55f,
                        sweepAngle = bottomAngle,
                        useCenter = false,
                        style = Stroke(width = GRID_2.toPx())
                    )
                }
            }

            val startPadding = GRID_34

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = writerText,
                    color = WriterMeTheme.colors.light,
                    style = MaterialTheme.typography.h1.copy(
                        fontSize = FONT_40,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(
                        start = startPadding,
                        top = GRID_120
                    )
                )
            }

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = ideasText,
                    color = WriterMeTheme.colors.light,
                    style = MaterialTheme.typography.h3.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(
                        start = startPadding,
                        top = GRID_24
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun GreetingScreenPreview() {
    AppTheme {
        GreetingComponent({})
    }
}
