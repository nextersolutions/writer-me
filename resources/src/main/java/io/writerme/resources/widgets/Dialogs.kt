package io.writerme.resources.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.BookmarksFolderViewData
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_1
import io.writerme.resources.common.Dimens.GRID_15
import io.writerme.resources.common.Dimens.GRID_16
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.common.Dimens.GRID_25
import io.writerme.resources.common.Dimens.GRID_30
import io.writerme.resources.common.Dimens.GRID_300
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.WEIGHT_08
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.blurRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.extensions.textFieldBackground
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Cards.FolderPickerRow
import io.writerme.resources.widgets.Dialogs.CreateBookmarkDialog
import io.writerme.resources.widgets.Dialogs.CreateFolderDialogBody
import io.writerme.resources.widgets.Spacers.SpacerVertical

object Dialogs {

    @Composable
    fun CreateBookmarkDialog(
        createBookmark: (url: String, title: String, parent: BookmarksFolderViewData) -> Unit,
        bookmarksFolder: BookmarksFolderViewData,
        onDismiss: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        val shape = RoundedCornerShape(GRID_25)
        val padding = GRID_16

        var url by remember {
            mutableStateOf(EMPTY)
        }

        var title by remember {
            mutableStateOf(EMPTY)
        }

        var path by remember {
            mutableStateOf("Path")
        }

        var isFolderChoosingMode by remember {
            mutableStateOf(false)
        }

        var folder by remember {
            mutableStateOf(bookmarksFolder)
        }

        Dialog(
            onDismissRequest = onDismiss
        ) {
            Card(
                shape = shape,
                modifier = modifier
                    .wrapContentHeight()
                    .shadow(GRID_15, shape)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(WriterMeTheme.colors.dialogBackground)
                            .blur(GRID_20)
                    )

                    Column(
                        modifier = Modifier.padding(padding)
                    ) {
                        Row(
                            modifier = Modifier.padding(GRID_0, GRID_8, GRID_0, padding),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedVisibility(visible = isFolderChoosingMode) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_back),
                                    contentDescription = stringResource(id = R.string.back_button),
                                    modifier = Modifier
                                        .size(GRID_30)
                                        .padding(end = GRID_16)
                                        .clickable { isFolderChoosingMode = false },
                                    tint = WriterMeTheme.colors.light
                                )
                            }

                            Text(
                                text = if (isFolderChoosingMode) {
                                    stringResource(id = R.string.choose_folder)
                                } else {
                                    stringResource(id = R.string.create_bookmark_title)
                                },
                                style = MaterialTheme.typography.h4,
                                color = WriterMeTheme.colors.light
                            )
                        }

                        Divider(
                            thickness = GRID_1,
                            color = WriterMeTheme.colors.strokeLight
                        )

                        Spacer(modifier = Modifier.height(padding))

                        val fieldShape =
                            RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

                        AnimatedVisibility(visible = !isFolderChoosingMode) {
                            Column {
                                BasicTextField(
                                    value = url,
                                    maxLines = VALUE_1,
                                    onValueChange = { url = it },
                                    modifier = Modifier
                                        .textFieldBackground()
                                        .fillMaxWidth(),
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
                                            if (url.isEmpty()) {
                                                Text(
                                                    text = stringResource(id = R.string.link),
                                                    style = MaterialTheme.typography.body1,
                                                    color = WriterMeTheme.colors.light
                                                )
                                            }
                                            innerTextField()
                                        }
                                    }
                                )

                                SpacerVertical(padding)

                                BasicTextField(
                                    value = title,
                                    maxLines = 1,
                                    onValueChange = { title = it },
                                    singleLine = true,
                                    modifier = Modifier
                                        .textFieldBackground()
                                        .fillMaxWidth(),
                                    textStyle = MaterialTheme.typography.body1.copy(
                                        color = WriterMeTheme.colors.light
                                    ),
                                    cursorBrush = SolidColor(WriterMeTheme.colors.light),
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            if (title.isEmpty()) {
                                                Text(
                                                    text = stringResource(id = R.string.title),
                                                    style = MaterialTheme.typography.body1,
                                                    color = WriterMeTheme.colors.light
                                                )
                                            }
                                            innerTextField()
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.height(padding))

                                Row(
                                    modifier = Modifier
                                        .textFieldBackground()
                                        .fillMaxWidth()
                                        .clickable {
                                            if (bookmarksFolder.folders.isNotEmpty()) {
                                                isFolderChoosingMode = true
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = path,
                                        modifier = Modifier.weight(WEIGHT_08),
                                        color = WriterMeTheme.colors.light,
                                        style = MaterialTheme.typography.body1
                                    )
                                    if (bookmarksFolder.folders.isNotEmpty()) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_folder),
                                            contentDescription = stringResource(id = R.string.folder_icon),
                                            modifier = Modifier.size(GRID_20),
                                            tint = WriterMeTheme.colors.light
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(padding))

                                Text(
                                    text = stringResource(id = R.string.create),
                                    modifier = Modifier
                                        .textFieldBackground()
                                        .wrapContentHeight()
                                        .padding(GRID_20, GRID_0)
                                        .align(Alignment.CenterHorizontally)
                                        .clickable {
                                            createBookmark(url, title, folder)
                                            onDismiss()
                                        },
                                    color = WriterMeTheme.colors.light
                                )
                            }
                        }

                        AnimatedVisibility(visible = isFolderChoosingMode) {
                            Column {
                                LazyColumn(
                                    modifier = Modifier
                                        .clip(fieldShape)
                                        .border(
                                            width = GRID_1,
                                            color = WriterMeTheme.colors.strokeLight,
                                            shape = fieldShape
                                        )
                                        .background(WriterMeTheme.colors.fieldDark)
                                        .heightIn(GRID_0, GRID_300)
                                        .padding(GRID_16, GRID_8)
                                        .fillMaxWidth()
                                ) {
                                    if (folder.hasParentFolder()) {
                                        item {
                                            FolderPickerRow(
                                                folder = BookmarksFolderViewData(name = ".."),
                                                modifier = Modifier.clickable {
                                                    folder = folder.parent!!
                                                }
                                            )
                                        }
                                    }

                                    items(
                                        items = bookmarksFolder.folders,
                                        itemContent = { item ->
                                            FolderPickerRow(
                                                folder = item,
                                                modifier = Modifier.clickable {
                                                    folder = item
                                                }
                                            )
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(padding))

                                Text(
                                    text = stringResource(id = R.string.choose),
                                    modifier = Modifier
                                        .textFieldBackground()
                                        .wrapContentHeight()
                                        .padding(20.dp, 0.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .clickable {
                                            isFolderChoosingMode = false
                                            path = folder.path
                                        },
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
    fun CreateFolderDialogBody(
        createFolder: (name: String) -> Unit,
        onDismiss: () -> Unit
    ) {
        var name by remember {
            mutableStateOf(EMPTY)
        }

        Column(
            modifier = Modifier.padding(screenPadding)
        ) {
            BasicTextField(
                value = name,
                maxLines = VALUE_1,
                onValueChange = { name = it },
                modifier = Modifier
                    .textFieldBackground()
                    .fillMaxWidth(),
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
                                text = stringResource(id = R.string.folder_name),
                                style = MaterialTheme.typography.body1,
                                color = WriterMeTheme.colors.light
                            )
                        }
                        innerTextField()
                    }
                }
            )

            SpacerVertical(screenPadding)

            Text(
                text = stringResource(id = R.string.create),
                modifier = Modifier
                    .textFieldBackground()
                    .wrapContentHeight()
                    .padding(horizontal = GRID_20)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        createFolder(name)
                        onDismiss()
                    },
                color = WriterMeTheme.colors.light
            )
        }
    }

    @Composable
    fun TitledDialog(
        title: String,
        onDismiss: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        val shape = RoundedCornerShape(bigRadius)

        Dialog(
            onDismissRequest = onDismiss
        ) {
            Card(
                shape = shape,
                modifier = modifier
                    .wrapContentHeight()
                    .shadow(
                        elevation = shadowRadius,
                        shape = shape
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(WriterMeTheme.colors.dialogBackground)
                            .blur(blurRadius)
                    )

                    Column(
                        modifier = Modifier.padding(screenPadding)
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                top = GRID_8,
                                bottom = screenPadding
                            ),
                            text = title,
                            style = MaterialTheme.typography.h4,
                            color = WriterMeTheme.colors.light
                        )

                        Divider(
                            thickness = GRID_1,
                            color = WriterMeTheme.colors.strokeLight
                        )

                        SpacerVertical(screenPadding)

                        content()
                    }
                }
            }
        }
    }

    @Composable
    fun AddLinkDialogBody(
        addLink: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        Column {
            var url by remember { mutableStateOf(EMPTY) }

            BasicTextField(
                value = url,
                maxLines = VALUE_1,
                onValueChange = { url = it },
                modifier = Modifier
                    .textFieldBackground()
                    .fillMaxWidth(),
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
                        if (url.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.url),
                                style = MaterialTheme.typography.body1,
                                color = WriterMeTheme.colors.light
                            )
                        }
                        innerTextField()
                    }
                }
            )

            SpacerVertical(screenPadding)

            Text(
                text = stringResource(id = R.string.add),
                modifier = Modifier
                    .textFieldBackground()
                    .wrapContentHeight()
                    .padding(horizontal = GRID_20)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        addLink(url)
                        onDismiss()
                    },
                color = WriterMeTheme.colors.light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateBookmarkDialogPreview() {
    var mainFolder = BookmarksFolderViewData()

    mainFolder = mainFolder.copy(
        folders = listOf(
            BookmarksFolderViewData(
                name = "Job",
                parent = mainFolder
            ),
            BookmarksFolderViewData(
                name = "Programming",
                parent = mainFolder
            ),
            BookmarksFolderViewData(
                name = "Films",
                parent = mainFolder
            )
        ),
        bookmarks = listOf(
            ComponentViewData(
                type = ComponentType.Link,
                title = "Top Travel Guide"
            ),
            ComponentViewData(
                type = ComponentType.Link,
                title = "Houses for Rent: Your best option"
            )
        )
    )

    AppTheme {
        CreateBookmarkDialog(
            createBookmark = { _, _, _ -> },
            onDismiss = {},
            bookmarksFolder = mainFolder
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateFolderDialogPreview() {
    AppTheme {
        CreateFolderDialogBody(createFolder = {}, onDismiss = {})
    }
}
