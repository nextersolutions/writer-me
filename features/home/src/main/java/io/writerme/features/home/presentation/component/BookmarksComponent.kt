package io.writerme.features.home.presentation.component

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.core.common.FormatUtils.VALUE_2
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.BookmarksFolderViewData
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_1
import io.writerme.resources.common.Dimens.GRID_110
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_120
import io.writerme.resources.common.Dimens.GRID_130
import io.writerme.resources.common.Dimens.GRID_15
import io.writerme.resources.common.Dimens.GRID_150
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.common.Dimens.GRID_40
import io.writerme.resources.common.Dimens.GRID_70
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Bookmarks.Folder
import io.writerme.resources.widgets.Bookmarks.Link
import io.writerme.resources.widgets.Dialogs.CreateBookmarkDialog
import io.writerme.resources.widgets.Dialogs.CreateFolderDialogBody
import io.writerme.resources.widgets.Dialogs.TitledDialog
import io.writerme.resources.widgets.Spacers.SpacerHorizontal

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookmarksComponent(
    currentFolder: BookmarksFolderViewData,
    isFloatingDialogShown: Boolean,
    isBookmarkDialogDisplayed: Boolean,
    isFolderDialogDisplayed: Boolean,
    onFolderClicked: (BookmarksFolderViewData) -> Unit,
    onLinkClicked: (ComponentViewData) -> Unit,
    toggleCreateFolderDialog: () -> Unit,
    toggleCreateBookmarkDialog: () -> Unit,
    toggleFloatingDialog: () -> Unit,
    navigateToParentFolder: () -> Unit,
    createBookmark: (String, String, BookmarksFolderViewData) -> Unit,
    createFolder: (String) -> Unit,
    onDeleteFolder: (BookmarksFolderViewData) -> Unit,
    deleteBookmark: (ComponentViewData) -> Unit,
    dismissScreen: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val padding = dimensionResource(id = R.dimen.screen_padding)

    // TODO
    /*checkAndRequestPermission(
        permission = Manifest.permission.INTERNET,
        onSuccess = {},
        onNotGrantedMessage = R.string.we_wont_load_images
    )*/

    BackHandler(
        onBack = {
            if (isBookmarkDialogDisplayed) {
                toggleCreateBookmarkDialog()
            } else if (isFolderDialogDisplayed) {
                toggleCreateFolderDialog()
            } else if (isFloatingDialogShown) {
                toggleFloatingDialog()
            } else if (currentFolder.hasParentFolder()) {
                navigateToParentFolder()
            }
        },
        enabled = currentFolder.hasParentFolder() || isFloatingDialogShown
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = GRID_0,
                    title = {
                        Text(
                            text = currentFolder.name.ifEmpty {
                                stringResource(id = R.string.bookmarks)
                            },
                            style = MaterialTheme.typography.h2,
                            color = WriterMeTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (currentFolder.hasParentFolder()) {
                                navigateToParentFolder()
                            } else {
                                dismissScreen()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = WriterMeTheme.colors.light
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            // implement in the future
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more),
                                contentDescription = stringResource(id = R.string.more),
                                tint = WriterMeTheme.colors.light
                            )
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .matchParentSize()
                            .padding(end = GRID_70),
                        horizontalArrangement = Arrangement.End
                    ) {
                        AnimatedVisibility(
                            visible = isFloatingDialogShown,
                            enter = slideInHorizontally(initialOffsetX = { it / VALUE_2 }) + fadeIn(),
                            exit = slideOutHorizontally(targetOffsetX = { it / VALUE_2 }) + fadeOut()
                        ) {
                            val shape =
                                RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

                            Card(
                                shape = shape,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .shadow(GRID_15, shape),
                                backgroundColor = Color.Transparent
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clip(shape)
                                        .background(WriterMeTheme.colors.dialogBackground)
                                        .padding(GRID_8)
                                        .shadow(GRID_15, shape),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.bookmark),
                                        modifier = Modifier
                                            .clip(shape)
                                            .background(WriterMeTheme.colors.fieldDark)
                                            .border(
                                                GRID_1,
                                                WriterMeTheme.colors.strokeLight,
                                                shape
                                            )
                                            .clickable {
                                                toggleCreateBookmarkDialog()
                                                toggleFloatingDialog()
                                            }
                                            .padding(padding, GRID_12)
                                            .height(GRID_40),
                                        color = WriterMeTheme.colors.light,
                                        textAlign = TextAlign.Center
                                    )

                                    SpacerHorizontal(GRID_8)

                                    Text(
                                        text = stringResource(id = R.string.folder),
                                        modifier = Modifier
                                            .clip(shape)
                                            .background(WriterMeTheme.colors.fieldDark)
                                            .border(
                                                GRID_1,
                                                WriterMeTheme.colors.strokeLight,
                                                shape
                                            )
                                            .clickable {
                                                toggleCreateFolderDialog()
                                                toggleFloatingDialog()
                                            }
                                            .padding(padding, GRID_1)
                                            .height(GRID_40),
                                        color = WriterMeTheme.colors.light,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            toggleFloatingDialog()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_folder_bookmark)
                        )
                    }
                }
            },
            content = {
                Column {
                    if (isBookmarkDialogDisplayed) {
                        CreateBookmarkDialog(
                            createBookmark = createBookmark,
                            bookmarksFolder = currentFolder,
                            onDismiss = toggleCreateBookmarkDialog
                        )
                    }

                    AnimatedVisibility(
                        visible = currentFolder.folders.isNotEmpty()
                    ) {
                        Row {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(GRID_120),
                                contentPadding = PaddingValues(padding),
                                content = {
                                    items(
                                        items = currentFolder.folders
                                    ) { item ->
                                        Folder(
                                            folder = item,
                                            onClick = onFolderClicked,
                                            onDelete = onDeleteFolder,
                                            modifier = Modifier
                                                .width(GRID_110)
                                                .padding(GRID_8)
                                        )
                                    }
                                }
                            )
                        }
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(GRID_150),
                            contentPadding = PaddingValues(padding),
                            content = {
                                val links = currentFolder.bookmarks

                                itemsIndexed(
                                    items = links
                                ) { index, item ->
                                    // TODO: !!!!!!!!!!!!!!
                                    val isExpanded = false

                                    ExposedDropdownMenuBox(
                                        expanded = isExpanded,
                                        onExpandedChange = { toggleBookmarkDropdown(index) }
                                    ) {
                                        Link(
                                            link = item,
                                            onClick = { onLinkClicked(item) },
                                            onLongClick = { toggleBookmarkDropdown(index) },
                                            modifier = Modifier
                                                .padding(GRID_8)
                                                .clickable(enabled = false, onClick = {}),
                                            height = GRID_130
                                        )

                                        MaterialTheme(
                                            colors = MaterialTheme.colors.copy(
                                                surface = WriterMeTheme.colors.light
                                            ),
                                            shapes = MaterialTheme.shapes.copy(
                                                medium = RoundedCornerShape(
                                                    dimensionResource(id = R.dimen.small_radius)
                                                )
                                            )
                                        ) {
                                            ExposedDropdownMenu(
                                                expanded = isExpanded,
                                                onDismissRequest = { toggleBookmarkDropdown(index) },
                                                scrollState = rememberScrollState()
                                            ) {
                                                DropdownMenuItem(onClick = {
                                                    deleteBookmark(item)
                                                    toggleBookmarkDropdown(index)
                                                }) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = stringResource(id = R.string.delete),
                                                            style = MaterialTheme.typography.body1
                                                        )

                                                        Icon(
                                                            imageVector = Icons.Default.Delete,
                                                            contentDescription = stringResource(id = R.string.delete),
                                                            modifier = Modifier.size(GRID_20),
                                                            tint = Color.DarkGray
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        )

        if (isFolderDialogDisplayed) {
            TitledDialog(
                title = stringResource(id = R.string.create_folder),
                onDismiss = toggleCreateFolderDialog,
                content = {
                    CreateFolderDialogBody(
                        createFolder = {
                            createFolder(it)
                            toggleCreateFolderDialog()
                        },
                        onDismiss = toggleCreateFolderDialog
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookmarksScreenPreview() {
    var mainFolder = BookmarksFolderViewData()
    val job = BookmarksFolderViewData(
        name = "Job",
        parent = mainFolder
    )

    mainFolder = mainFolder.copy(
        folders = listOf(
            job,
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
        BookmarksComponent(
            currentFolder = mainFolder,
            isFloatingDialogShown = false,
            isBookmarkDialogDisplayed = false,
            isFolderDialogDisplayed = false,
            onFolderClicked = {},
            onLinkClicked = {},
            toggleCreateFolderDialog = {},
            toggleCreateBookmarkDialog = {},
            toggleFloatingDialog = {},
            navigateToParentFolder = {},
            createBookmark = { _, _, _ -> },
            createFolder = {},
            onDeleteFolder = {},
            deleteBookmark = {},
            toggleFolderDropdown = {},
            toggleBookmarkDropdown = {},
            dismissScreen = {}
        )
    }
}
