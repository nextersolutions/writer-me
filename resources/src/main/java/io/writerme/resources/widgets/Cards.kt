package io.writerme.resources.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.core.contracts.handlers.MediaListener
import io.writerme.core.extensions.toTime
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.AudioStateViewData
import io.writerme.core.models.viewdata.BookmarksFolderViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_24
import io.writerme.resources.common.Dimens.GRID_30
import io.writerme.resources.common.Dimens.GRID_4
import io.writerme.resources.common.Dimens.GRID_6
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.WEIGHT_MAX
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Cards.Counter
import io.writerme.resources.widgets.Cards.SettingsCounterRow
import io.writerme.resources.widgets.Spacers.SpacerHorizontal
import io.writerme.resources.widgets.Spacers.SpacerVertical

object Cards {
    @Composable
    fun Counter(
        value: Int,
        increaseValueId: Int,
        decreaseValueId: Int,
        onChange: (increase: Boolean) -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(bigRadius)
                )
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = stringResource(id = increaseValueId),
                modifier = Modifier
                    .padding(
                        start = GRID_4,
                        top = GRID_4,
                        bottom = GRID_4
                    )
                    .clickable {
                        onChange(false)
                    }
            )

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(
                    horizontal = GRID_16,
                    vertical = GRID_6
                )
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(id = decreaseValueId),
                modifier = Modifier
                    .padding(
                        top = GRID_4,
                        bottom = GRID_4,
                        end = GRID_4
                    )
                    .clickable {
                        onChange(true)
                    }
            )
        }
    }

    @Composable
    fun FolderPickerRow(
        folder: BookmarksFolderViewData,
        modifier: Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(
                horizontal = GRID_0,
                vertical = GRID_8
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_folder),
                contentDescription = stringResource(id = R.string.folder_icon),
                modifier = Modifier.size(GRID_30),
                tint = WriterMeTheme.colors.light
            )

            Text(
                text = folder.name,
                color = WriterMeTheme.colors.light,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(
                    start = GRID_24,
                    end = GRID_8
                ),
            )
        }
    }


    @Composable
    fun SettingsCounterRow(
        stringId: Int,
        value: Int,
        increaseValueId: Int,
        decreaseValueId: Int,
        onChange: (increase: Boolean) -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = screenPadding,
                    vertical = GRID_8
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = stringId),
                style = MaterialTheme.typography.body1,
                color = WriterMeTheme.colors.light,
                modifier = Modifier.weight(WEIGHT_MAX)
            )

            SpacerHorizontal(screenPadding)

            Counter(
                value = value,
                onChange = onChange,
                increaseValueId = increaseValueId,
                decreaseValueId = decreaseValueId
            )
        }
    }

    @Composable
    fun Audio(
        state: AudioStateViewData,
        audioListener: MediaListener,
        modifier: Modifier = Modifier
    ) {
        if (state.audio.type == ComponentType.Voice) {
            val shape = RoundedCornerShape(bigRadius)

            Card(
                shape = shape,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
                    .shadow(shadowRadius, shape),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(WriterMeTheme.colors.backgroundGrey)
                    )

                    Row(
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(GRID_16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        IconButton(onClick = { /*TODO*/ }) {
                            val isPaused = audioListener.isPaused()
                            Icon(
                                painter = if (isPaused) {
                                    painterResource(id = R.drawable.ic_play)
                                } else painterResource(id = R.drawable.ic_pause),

                                contentDescription = if (isPaused) {
                                    stringResource(id = R.string.play_button)
                                } else stringResource(id = R.string.pause_button),

                                tint = WriterMeTheme.colors.light
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row {
                                // TODO: here should be the progress bar
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // TODO: here will be progress indicators
                                Text(
                                    text = state.currentProgress.toTime(),
                                    style = MaterialTheme.typography.caption,
                                    color = WriterMeTheme.colors.light
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = state.entireLength.toTime(),
                                    style = MaterialTheme.typography.caption,
                                    color = WriterMeTheme.colors.light
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Cards_Preview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .padding(GRID_12)
        ) {
            Counter(1, 0, 0) { _: Boolean -> }

            SpacerVertical(GRID_16)

            SettingsCounterRow(
                stringId = R.string.number_of_text_changes,
                value = 5,
                increaseValueId = 0,
                decreaseValueId = 0,
                onChange = {}
            )
        }
    }
}
