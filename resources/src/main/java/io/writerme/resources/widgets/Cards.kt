package io.writerme.resources.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import io.writerme.app.ui.component.HeartShape
import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.common.FormatUtils.VALUE_2
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.core.common.FormatUtils.animationDuration
import io.writerme.core.contracts.handlers.MediaListener
import io.writerme.core.extensions.toTime
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.AudioStateViewData
import io.writerme.core.models.viewdata.BookmarksFolderViewData
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.core.models.viewdata.HistoryViewData
import io.writerme.core.models.viewdata.NoteViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_1
import io.writerme.resources.common.Dimens.GRID_10
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_120
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_24
import io.writerme.resources.common.Dimens.GRID_3
import io.writerme.resources.common.Dimens.GRID_30
import io.writerme.resources.common.Dimens.GRID_4
import io.writerme.resources.common.Dimens.GRID_400
import io.writerme.resources.common.Dimens.GRID_6
import io.writerme.resources.common.Dimens.GRID_60
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.NO_WEIGHT
import io.writerme.resources.common.Dimens.WEIGHT_085
import io.writerme.resources.common.Dimens.WEIGHT_MAX
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.common.Dimens.smallRadius
import io.writerme.resources.common.Dimens.tabWidth
import io.writerme.resources.extensions.displayName
import io.writerme.resources.extensions.toDateDescription
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Cards.Counter
import io.writerme.resources.widgets.Cards.Note
import io.writerme.resources.widgets.Cards.NoteText
import io.writerme.resources.widgets.Cards.SettingsCounterRow
import io.writerme.resources.widgets.Cards.TabSwitcher
import io.writerme.resources.widgets.Images.Image
import io.writerme.resources.widgets.Spacers.SpacerHorizontal
import io.writerme.resources.widgets.Spacers.SpacerVertical
import io.writerme.resources.widgets.Spacers.SpacerWeightView
import java.util.Calendar

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
                )
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
            verticalAlignment = Alignment.CenterVertically
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
                    .shadow(shadowRadius, shape)
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
                                } else {
                                    painterResource(id = R.drawable.ic_pause)
                                },

                                contentDescription = if (isPaused) {
                                    stringResource(id = R.string.play_button)
                                } else {
                                    stringResource(id = R.string.pause_button)
                                },

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

                                SpacerWeightView(WEIGHT_MAX)

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

    @Composable
    fun Note(
        note: NoteViewData,
        modifier: Modifier = Modifier
    ) {
        val verticalSpacing = screenPadding / VALUE_2

        val shape = RoundedCornerShape(bigRadius)

        Card(
            shape = shape,
            modifier = modifier
                .wrapContentHeight()
                .heightIn(GRID_120, GRID_400)
                .shadow(shadowRadius, shape),
            backgroundColor = Color.White
        ) {
            if (note.cover != null && note.cover?.newest() != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Transparent)
                ) {
                    AsyncImage(
                        model = note.cover!!.newest()!!.mediaUrl,
                        contentDescription = note.cover!!.newest()!!.content,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    if (
                        note.title != null && note.title!!.newest() != null &&
                        note.title!!.newest()!!.title.isNotEmpty()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .padding(
                                    start = GRID_12,
                                    top = GRID_0,
                                    end = GRID_12,
                                    bottom = GRID_12
                                )
                                .clip(
                                    RoundedCornerShape(bigRadius)
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(WriterMeTheme.colors.lightGrey)
                                    .blur(GRID_60)
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = screenPadding,
                                        vertical = GRID_4
                                    )
                            ) {
                                note.title?.newest()?.let { component ->
                                    Text(
                                        text = component.content,
                                        style = MaterialTheme.typography.h5,
                                        modifier = Modifier.fillMaxWidth(),
                                        maxLines = VALUE_1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                if (note.content.isNotEmpty() && note.content.first().isNotEmpty() &&
                                    note.content.first().newest()!!.type == ComponentType.Text
                                ) {
                                    Text(
                                        text = note.content.first().newest()!!.content,
                                        style = MaterialTheme.typography.body2,
                                        modifier = Modifier.fillMaxWidth(),
                                        maxLines = VALUE_1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(screenPadding)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            note.title?.newest()?.let { component ->
                                Text(
                                    text = component.content,
                                    style = MaterialTheme.typography.h5,
                                    modifier = Modifier.fillMaxWidth(WEIGHT_085),
                                    maxLines = VALUE_2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            if (note.isImportant) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_heart),
                                    contentDescription = stringResource(id = R.string.note_important_icon),
                                    modifier = Modifier
                                        .shadow(
                                            elevation = GRID_4,
                                            shape = HeartShape()
                                        )
                                )
                            }
                        }
                    }

                    item {
                        SpacerVertical(verticalSpacing)

                        LazyRow(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(note.tags) { tag ->
                                Text(
                                    text = "#$tag",
                                    style = MaterialTheme.typography.caption,
                                    modifier = Modifier
                                        .clip(
                                            RoundedCornerShape(screenPadding)
                                        )
                                        .background(WriterMeTheme.colors.lightGrey)
                                        .padding(screenPadding, GRID_4)
                                )

                                SpacerHorizontal(GRID_8)
                            }
                        }
                    }

                    items(items = note.content) { history ->
                        history.newest()?.let { component ->
                            SpacerVertical(verticalSpacing)

                            when (component.type) {
                                ComponentType.Text -> {
                                    Text(
                                        text = component.content,
                                        style = MaterialTheme.typography.body2
                                    )
                                }

                                ComponentType.Checkbox -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        SpacerHorizontal(GRID_4)

                                        Icon(
                                            modifier = Modifier
                                                .size(GRID_10)
                                                .padding(top = GRID_3),
                                            painter = when (component.isChecked) {
                                                true -> painterResource(id = R.drawable.ic_checked)
                                                false -> painterResource(id = R.drawable.ic_unchecked)
                                            },
                                            contentDescription = stringResource(id = R.string.checkbox_name)
                                        )

                                        SpacerHorizontal(GRID_6)

                                        Text(
                                            text = component.content,
                                            style = MaterialTheme.typography.body2,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }

                                ComponentType.Voice -> {
                                    // pending
                                }

                                ComponentType.Task -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(WEIGHT_MAX)
                                        ) {
                                            Text(
                                                text = component.time.toDateDescription(),
                                                style = MaterialTheme.typography.h5,
                                                modifier = Modifier.fillMaxWidth(),
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = VALUE_1
                                            )
                                            Text(
                                                text = component.content,
                                                style = MaterialTheme.typography.h6,
                                                modifier = Modifier.fillMaxWidth(),
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = VALUE_1
                                            )
                                        }

                                        SpacerHorizontal(screenPadding)

                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_proceed),
                                            contentDescription = null
                                        )
                                    }
                                }

                                ComponentType.Link -> {
                                }

                                ComponentType.Video -> {
                                    // pending...
                                }

                                ComponentType.Image -> {
                                    Image(component = component)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Video() {
        // Implementation pending
    }

    @Composable
    fun SettingsSectionTitle(
        titleRes: Int,
        iconRes: Int? = null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.h4,
                color = WriterMeTheme.colors.light
            )

            iconRes?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = stringResource(id = R.string.icon),
                    tint = WriterMeTheme.colors.light
                )
            }
        }
    }

    @Composable
    fun NoteText(
        component: ComponentViewData,
        onValueChange: (ComponentViewData) -> Unit,
        placeholderResource: Int = R.string.type_your_note_here,
        typography: TextStyle = MaterialTheme.typography.subtitle1,
        modifier: Modifier = Modifier
    ) {
        var localText by remember {
            mutableStateOf(EMPTY)
        }

        if (localText.isEmpty()) {
            localText = component.content
        }

        if (component.type == ComponentType.Text) {
            BasicTextField(
                value = localText,
                onValueChange = {
                    localText = it
                    onValueChange(
                        component.copy(content = it)
                    )
                },
                modifier = modifier,
                textStyle = typography.copy(
                    color = WriterMeTheme.colors.light
                ),
                cursorBrush = SolidColor(WriterMeTheme.colors.light),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (localText.isEmpty()) {
                            Text(
                                text = stringResource(id = placeholderResource),
                                style = typography,
                                color = WriterMeTheme.colors.light
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }

    @Composable
    fun TabSwitcher(
        chosen: HomeFilterTab,
        onItemChosen: (HomeFilterTab) -> Unit
    ) {
        val tabs = listOf(
            HomeFilterTab.All,
            HomeFilterTab.Important
        )
        val shape = RoundedCornerShape(smallRadius)

        val offset by animateDpAsState(
            targetValue = try {
                val index = tabs.indexOf(chosen)
                tabWidth * index
            } catch (e: Exception) {
                GRID_0
            },
            animationSpec = tween(durationMillis = animationDuration),
            label = EMPTY
        )

        Layout(
            modifier = Modifier
                .background(
                    color = WriterMeTheme.colors.lightTransparent,
                    shape = shape
                )
                .border(
                    width = GRID_1,
                    color = WriterMeTheme.colors.strokeLight,
                    shape = shape
                )
                .padding(vertical = GRID_4),
            content = {
                // Slider Box
                Box(
                    modifier = Modifier
                        .width(tabWidth)
                        .offset(x = offset)
                        .padding(horizontal = GRID_4)
                        .background(
                            color = WriterMeTheme.colors.lightTransparent,
                            shape = shape
                        )
                        .border(
                            width = GRID_1,
                            color = WriterMeTheme.colors.strokeLight,
                            shape = shape
                        )
                        .padding(vertical = GRID_4)
                )

                // Tabs Row
                Row {
                    tabs.forEach {
                        Text(
                            text = it.displayName(),
                            style = MaterialTheme.typography.subtitle1,
                            color = WriterMeTheme.colors.light,
                            modifier = Modifier
                                .width(tabWidth)
                                .padding(GRID_4)
                                .clickable {
                                    if (it != chosen) onItemChosen(it)
                                },
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        ) { measurables, constraints ->
            // Measure the Row
            val rowMeasurable = measurables[VALUE_1]
            val rowPlaceable = rowMeasurable.measure(constraints)

            // Use the height of the Row to constrain the height of the Slider Box
            val boxMeasurable = measurables[ZERO]
            val boxPlaceable = boxMeasurable.measure(
                constraints.copy(minHeight = rowPlaceable.height)
            )

            // Determine the width and height of the Layout
            val width = maxOf(boxPlaceable.width, rowPlaceable.width)
            val height = rowPlaceable.height

            layout(width, height) {
                boxPlaceable.place(ZERO, ZERO, NO_WEIGHT)
                rowPlaceable.place(ZERO, ZERO, WEIGHT_MAX)
            }
        }
    }
}

@Preview
@Composable
private fun Cards_Preview() {
    val component = ComponentViewData(
        type = ComponentType.Text,
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry..."
    )

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

            SpacerVertical(GRID_16)

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR, 5)

            val titleComponent = ComponentViewData(
                title = "Instagram Content Plan",
                type = ComponentType.Text
            )

            val text = ComponentViewData(
                content = "I hope you enjoy it. Feel free to share your thoughts in the following section.",
                type = ComponentType.Text
            )

            val note = NoteViewData(
                title = HistoryViewData(changes = listOf(titleComponent)),
                content = listOf(
                    HistoryViewData(changes = listOf(text)),
                    HistoryViewData(
                        changes = listOf(
                            ComponentViewData(
                                noteId = EMPTY,
                                time = calendar.time,
                                content = "Meeting with Anna"
                            )
                        )
                    )
                ),
                isImportant = true,
                tags = listOf("project")
            )

            Note(note)

            SpacerVertical(GRID_16)

            NoteText(
                component = component,
                onValueChange = {}
            )

            SpacerVertical(GRID_16)

            TabSwitcher(HomeFilterTab.All) {}
        }
    }
}
