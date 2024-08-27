package io.writerme.features.home.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.SECOND
import io.writerme.core.common.FormatUtils.VALUE_2
import io.writerme.core.common.FormatUtils.animationDuration
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_30
import io.writerme.resources.common.Dimens.GRID_4
import io.writerme.resources.common.Dimens.GRID_50
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.WEIGHT_065
import io.writerme.resources.common.Dimens.WEIGHT_07
import io.writerme.resources.common.Dimens.WEIGHT_MAX
import io.writerme.resources.extensions.textFieldBackground
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Spacers.SpacerHorizontal
import io.writerme.resources.widgets.Spacers.SpacerVertical
import io.writerme.resources.widgets.Spacers.SpacerWeightView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegistrationComponent(
    saveName: (String) -> Unit,
    proceedToNextScreen: () -> Unit
) {
    val typing = stringResource(id = R.string.typing)

    var name by remember {
        mutableStateOf(EMPTY)
    }

    var sentName by remember {
        mutableStateOf(EMPTY)
    }

    var isFirstMessageVisible by remember {
        mutableStateOf(false)
    }

    var isSecondMessageVisible by remember {
        mutableStateOf(false)
    }

    var isUserMessageVisible by remember {
        mutableStateOf(false)
    }

    var isLastMessageVisible by remember {
        mutableStateOf(false)
    }

    var isTyping by remember {
        mutableStateOf(true)
    }

    var typingText by remember {
        mutableStateOf(typing)
    }

    val send: () -> Unit = {
        if (name.length > VALUE_2) {
            sentName = name
            name = EMPTY
            isUserMessageVisible = true

            saveName(sentName)
            isLastMessageVisible = true
        }
    }

    LaunchedEffect(key1 = null) {
        launch {
            var millis = 0
            var counter = 1
            while (counter <= 3) {
                typingText = when (counter) {
                    1 -> "$typing."
                    2 -> "$typing.."
                    3 -> {
                        counter = 1
                        "$typing..."
                    }

                    else -> typing
                }
                counter++
                delay(300)

                millis += 100

                if (millis == 900) isFirstMessageVisible = true
                if (millis == 2400) break
            }
            isTyping = false
            isSecondMessageVisible = true
        }
    }

    if (isLastMessageVisible) {
        LaunchedEffect(key1 = null) {
            delay(SECOND)
            proceedToNextScreen()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = stringResource(id = R.string.background_image),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(GRID_16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_writer_me),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.size(GRID_50)
                )

                SpacerHorizontal(GRID_8)

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h1,
                    color = WriterMeTheme.colors.light
                )
            }

            SpacerWeightView()

            AnimatedVisibility(
                visible = isFirstMessageVisible,
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = animationDuration)
                ) + scaleIn(),
                exit = slideOutHorizontally() + scaleOut()
            ) {
                Text(
                    text = stringResource(id = R.string.hi),
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = WriterMeTheme.colors.light.copy(alpha = WEIGHT_07),
                    modifier = Modifier
                        .fillMaxWidth(WEIGHT_065)
                        .background(
                            color = WriterMeTheme.colors.dropdownBackground,
                            shape = RoundedCornerShape(
                                topStart = GRID_8,
                                topEnd = GRID_8,
                                bottomEnd = GRID_0,
                                bottomStart = GRID_0
                            )
                        )
                        .padding(GRID_12)

                )
            }

            if (isSecondMessageVisible) {
                SpacerVertical(GRID_4)
            }

            AnimatedVisibility(
                visible = isSecondMessageVisible,
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = animationDuration)
                ) + scaleIn(),
                exit = slideOutHorizontally() + scaleOut()
            ) {
                Text(
                    text = stringResource(id = R.string.introduce),
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = WriterMeTheme.colors.light.copy(
                        alpha = WEIGHT_07
                    ),
                    modifier = Modifier
                        .fillMaxWidth(WEIGHT_065)
                        .background(
                            color = WriterMeTheme.colors.dropdownBackground,
                            shape = RoundedCornerShape(
                                topStart = GRID_0,
                                topEnd = GRID_0,
                                bottomEnd = GRID_8,
                                bottomStart = GRID_0
                            )
                        )
                        .padding(GRID_12)

                )
            }

            if (isUserMessageVisible) {
                SpacerVertical(GRID_8)
            }

            AnimatedVisibility(
                visible = isUserMessageVisible,
                enter = slideInHorizontally(initialOffsetX = { it / VALUE_2 }) + scaleIn(),
                exit = slideOutHorizontally(targetOffsetX = { it / VALUE_2 }) + scaleOut(),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = sentName,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .fillMaxWidth(WEIGHT_065)
                        .background(
                            color = WriterMeTheme.colors.strokeLight,
                            shape = RoundedCornerShape(
                                topStart = GRID_8,
                                topEnd = GRID_8,
                                bottomEnd = GRID_0,
                                bottomStart = GRID_8
                            )
                        )
                        .padding(GRID_12)

                )
            }

            if (isLastMessageVisible) {
                SpacerVertical(GRID_8)
            }

            AnimatedVisibility(
                visible = isLastMessageVisible,
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = animationDuration)
                ) + scaleIn(),
                exit = slideOutHorizontally() + scaleOut()
            ) {
                Text(
                    text = stringResource(id = R.string.saving),
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = WriterMeTheme.colors.light.copy(alpha = WEIGHT_07),
                    modifier = Modifier
                        .fillMaxWidth(WEIGHT_065)
                        .background(
                            color = WriterMeTheme.colors.dropdownBackground,
                            shape = RoundedCornerShape(
                                topStart = GRID_8,
                                topEnd = GRID_8,
                                bottomEnd = GRID_8,
                                bottomStart = GRID_0
                            )
                        )
                        .padding(GRID_12)

                )
            }

            if (isTyping) {
                SpacerVertical(GRID_16)
            }

            AnimatedVisibility(
                visible = isTyping,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = typingText,
                    style = MaterialTheme.typography.body1,
                    color = WriterMeTheme.colors.lightGrey
                )
            }

            SpacerVertical(GRID_16)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = name,
                    maxLines = 1,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier
                        .textFieldBackground()
                        .weight(WEIGHT_MAX)
                        .onKeyEvent {
                            if (it.type == KeyEventType.KeyDown) {
                                send()
                                true
                            } else {
                                false
                            }
                        },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = WriterMeTheme.colors.light
                    ),
                    cursorBrush = SolidColor(WriterMeTheme.colors.light),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (name.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.full_name),
                                    style = MaterialTheme.typography.body1,
                                    color = WriterMeTheme.colors.lightGrey
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                IconButton(
                    onClick = {
                        send()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = stringResource(id = R.string.send_button),
                        tint = WriterMeTheme.colors.lightGrey,
                        modifier = Modifier.size(GRID_30)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    AppTheme {
        RegistrationComponent({}, {})
    }
}
