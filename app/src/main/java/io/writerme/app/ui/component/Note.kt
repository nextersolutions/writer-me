package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.writerme.app.R
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.data.viewdata.HistoryViewData
import io.writerme.app.data.viewdata.NoteViewData
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.lightGrey
import io.writerme.app.utils.toDateDescription
import java.util.Date

@Composable
fun Note(
    note: NoteViewData,
    modifier: Modifier = Modifier
) {
    val padding = dimensionResource(id = R.dimen.screen_padding)
    val verticalSpacing = padding / 2

    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

    Card(
        shape = shape,
        modifier = modifier
            .wrapContentHeight()
            .heightIn(120.dp, 400.dp)
            .shadow(dimensionResource(id = R.dimen.shadow), shape),
        backgroundColor = Color.White
    ) {
        val coverComponent = note.cover?.newest()
        if (coverComponent != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
            ) {
                AsyncImage(
                    model = coverComponent.mediaUrl,
                    contentDescription = coverComponent.content,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                val titleComponent = note.title?.newest()
                if (titleComponent != null && titleComponent.content.isNotEmpty()) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(12.dp, 0.dp, 12.dp, 12.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.big_radius)))
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colors.lightGrey)
                                .blur(60.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding, 4.dp)
                        ) {
                            Text(
                                text = titleComponent.content,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            val firstContent = note.content.firstOrNull()?.newest()
                            if (firstContent != null && firstContent.type == ComponentType.Text) {
                                Text(
                                    text = firstContent.content,
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.fillMaxWidth(),
                                    maxLines = 1,
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
                    .padding(padding)
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
                                modifier = Modifier.fillMaxWidth(0.85f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (note.isImportant) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_heart),
                                contentDescription = stringResource(id = R.string.note_important_icon),
                                modifier = Modifier
                                    .shadow(elevation = 4.dp, HeartShape())
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(verticalSpacing))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(note.tags) { tag ->
                            Text(
                                text = "#$tag",
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(padding))
                                    .background(MaterialTheme.colors.lightGrey)
                                    .padding(padding, 4.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }

                items(items = note.content) { history ->
                    history.newest()?.let { component ->
                        Spacer(modifier = Modifier.height(verticalSpacing))

                        when (component.type) {
                            ComponentType.Text -> {
                                Text(
                                    text = component.content,
                                    style = MaterialTheme.typography.body2
                                )
                            }

                            ComponentType.Checkbox -> {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Spacer(Modifier.width(4.dp))

                                    Icon(
                                        modifier = Modifier
                                            .width(10.dp)
                                            .height(10.dp)
                                            .padding(top = 3.dp),
                                        painter = if (component.isChecked) painterResource(id = R.drawable.ic_checked)
                                        else painterResource(id = R.drawable.ic_unchecked),
                                        contentDescription = stringResource(id = R.string.checkbox_name)
                                    )
                                    Spacer(Modifier.width(6.dp))

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
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = component.time.toDateDescription(),
                                            style = MaterialTheme.typography.h5,
                                            modifier = Modifier.fillMaxWidth(1f),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = component.content,
                                            style = MaterialTheme.typography.h6,
                                            modifier = Modifier.fillMaxWidth(1f),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(padding))

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_proceed),
                                        contentDescription = null,
                                    )
                                }
                            }
                            ComponentType.Link -> {
                                // pending
                            }
                            ComponentType.Video -> {
                                // pending
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
@Preview(showBackground = true)
fun NotePreview() {
    val titleComponent = ComponentViewData.empty(ComponentType.Text).copy(
        content = "Instagram Content Plan"
    )
    val titleHistory = HistoryViewData(id = 1L, changes = listOf(titleComponent))

    val textComponent = ComponentViewData.empty(ComponentType.Text).copy(
        content = "I hope you enjoy it. Feel free to share your thoughts in the following section."
    )
    val textHistory = HistoryViewData(id = 2L, changes = listOf(textComponent))

    val note = NoteViewData(
        id = 1L,
        title = titleHistory,
        cover = null,
        content = listOf(textHistory),
        isImportant = true,
        created = Date(),
        changeTime = System.currentTimeMillis(),
        tags = listOf("project")
    )

    WriterMeTheme {
        Note(note)
    }
}
