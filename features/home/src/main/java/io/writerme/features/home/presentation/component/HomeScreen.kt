package io.writerme.features.home.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.core.common.FormatUtils.VALUE_2
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.core.models.viewdata.HistoryViewData
import io.writerme.core.models.viewdata.HomeViewData
import io.writerme.core.models.viewdata.NoteViewData
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_10
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.common.Dimens.GRID_40
import io.writerme.resources.common.Dimens.GRID_50
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.GRID_80
import io.writerme.resources.common.Dimens.WEIGHT_06
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.smallRadius
import io.writerme.resources.extensions.toGreeting
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Cards.Note
import io.writerme.resources.widgets.Cards.TabSwitcher
import io.writerme.resources.widgets.Images.ProfileImage
import io.writerme.resources.widgets.Spacers.SpacerHorizontal
import io.writerme.resources.widgets.Spacers.SpacerVertical
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    state: HomeViewData,
    toggleSearchMode: () -> Unit,
    openTasksScreen: () -> Unit,
    openBookmarksScreen: () -> Unit,
    openSettingsScreen: () -> Unit,
    onNoteClick: (String) -> Unit,
    createNote: () -> Unit,
    onTabChosen: (HomeFilterTab) -> Unit,
    toggleImportance: (String) -> Unit,
    deleteNote: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    val fabShape = RoundedCornerShape(GRID_50)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.background_image),
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = GRID_0,
                    modifier = Modifier.padding(top = GRID_8),
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.h2,
                            color = WriterMeTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_writer_me),
                            contentDescription = stringResource(id = R.string.back_button),
                            tint = WriterMeTheme.colors.light,
                            modifier = Modifier
                                .padding(start = screenPadding)
                                .height(GRID_40)
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = createNote,
                    shape = fabShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(id = R.string.create_note)
                    )
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.clip(
                        RoundedCornerShape(bigRadius)
                    ),
                    backgroundColor = WriterMeTheme.colors.backgroundGrey,
                    cutoutShape = fabShape

                ) {
                    BottomNavigation(
                        backgroundColor = Color.Transparent,
                        elevation = GRID_0
                    ) {
                        BottomNavigationItem(
                            selected = true,
                            onClick = toggleSearchMode,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_home),
                                    contentDescription = stringResource(id = R.string.home_screen),
                                    tint = WriterMeTheme.colors.light
                                )
                            }
                        )

                        BottomNavigationItem(
                            selected = false,
                            onClick = openTasksScreen,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_tasks),
                                    contentDescription = stringResource(id = R.string.tasks_screen),
                                    tint = WriterMeTheme.colors.light
                                )
                            }
                        )

                        SpacerHorizontal(GRID_80)

                        BottomNavigationItem(
                            selected = false,
                            onClick = openBookmarksScreen,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_bookmark),
                                    contentDescription = stringResource(id = R.string.bookmarks_screen),
                                    tint = WriterMeTheme.colors.light
                                )
                            }
                        )

                        BottomNavigationItem(
                            selected = false,
                            onClick = openSettingsScreen,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_settings),
                                    contentDescription = stringResource(id = R.string.settings_task),
                                    tint = WriterMeTheme.colors.light
                                )
                            }
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = screenPadding,
                        top = screenPadding,
                        end = screenPadding,
                        bottom = it.calculateBottomPadding() + screenPadding
                    )
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (state.profilePhotoUrl.isNotEmpty()) {
                        ProfileImage(
                            url = state.profilePhotoUrl,
                            onClick = openSettingsScreen
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(WEIGHT_06)
                            .padding(screenPadding, GRID_0)
                    ) {
                        Text(
                            text = Date().toGreeting(),
                            style = MaterialTheme.typography.body1,
                            color = WriterMeTheme.colors.light
                        )

                        Text(
                            text = state.firstName,
                            style = MaterialTheme.typography.h1,
                            color = WriterMeTheme.colors.light,
                            modifier = Modifier.padding(top = GRID_10)
                        )
                    }
                }

                if (state.isImportantVisible) {
                    SpacerVertical(screenPadding)
                }

                AnimatedVisibility(
                    visible = state.isImportantVisible,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    TabSwitcher(
                        chosen = state.chosenTab,
                        onItemChosen = onTabChosen
                    )
                }

                SpacerHorizontal(screenPadding)

                if (state.notes.isEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_notes),
                            textAlign = TextAlign.Center,
                            color = WriterMeTheme.colors.light
                        )
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(VALUE_2),
                        verticalItemSpacing = screenPadding,
                        horizontalArrangement = Arrangement.spacedBy(screenPadding),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(900.dp)
                    ) {
                        itemsIndexed(items = state.notes) { index, item ->
                            var isExpanded by remember {
                                mutableStateOf(false)
                            }

                            ExposedDropdownMenuBox(
                                expanded = isExpanded,
                                onExpandedChange = {}
                            ) {
                                Note(
                                    note = item,
                                    modifier = Modifier
                                        .combinedClickable(
                                            onLongClick = {
                                                isExpanded = true
                                            },
                                            onDoubleClick = {
                                                toggleImportance(item.id)
                                            },
                                            onClick = {
                                                onNoteClick(item.id)
                                            }
                                        )
                                )

                                MaterialTheme(
                                    colors = MaterialTheme.colors.copy(
                                        surface = WriterMeTheme.colors.light,
                                        background = Color.Blue
                                    ),
                                    shapes = MaterialTheme.shapes.copy(
                                        medium = RoundedCornerShape(smallRadius)
                                    )
                                ) {
                                    ExposedDropdownMenu(
                                        expanded = isExpanded,
                                        onDismissRequest = { isExpanded = false },
                                        scrollState = rememberScrollState()
                                    ) {
                                        DropdownMenuItem(onClick = {
                                            toggleImportance(item.id)
                                            isExpanded = !isExpanded
                                        }) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = if (item.isImportant) {
                                                        stringResource(id = R.string.not_important)
                                                    } else {
                                                        stringResource(id = R.string.important)
                                                    },
                                                    style = MaterialTheme.typography.body1
                                                )

                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_heart),
                                                    contentDescription = stringResource(id = R.string.important),
                                                    modifier = Modifier.size(GRID_20),
                                                    tint = Color.DarkGray
                                                )
                                            }
                                        }

                                        DropdownMenuItem(onClick = {
                                            deleteNote(item.id)
                                            isExpanded = !isExpanded
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
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.HOUR, 5)

    var note1 = NoteViewData()
    note1 = note1.copy(
        title = HistoryViewData(
            changes = listOf(
                ComponentViewData(
                    noteId = note1.id,
                    content = "Instagram Content Plan"
                )
            )
        ),
        isImportant = true,
        tags = listOf("project"),
        content = listOf(
            HistoryViewData(
                changes = listOf(
                    ComponentViewData(
                        noteId = note1.id,
                        content = "I hope you enjoy it. Feel free to share your thoughts in the following section..."
                    )
                )
            ),
            HistoryViewData(
                changes = listOf(
                    ComponentViewData(
                        noteId = note1.id,
                        time = calendar.time,
                        content = "Meeting with Anna"
                    )
                )
            )
        )
    )

    var note2 = NoteViewData()
    note2 = note2.copy(
        title = HistoryViewData(
            changes = listOf(
                ComponentViewData(
                    noteId = note2.id,
                    content = "Day #12: Adventure begins"
                )
            )
        ),
        cover = HistoryViewData(
            changes = listOf(
                ComponentViewData(
                    noteId = note2.id,
                    type = ComponentType.Image
                )
            )
        ),
        content = listOf(
            HistoryViewData(
                changes = listOf(
                    ComponentViewData(
                        noteId = note2.id,
                        content = "It’s all started with a post I saw on the Instagram."
                    )
                )
            )
        )
    )

    val main = HomeViewData(
        firstName = "Florian",
        chosenTab = HomeFilterTab.All,
        isImportantVisible = true,
        notes = listOf(note1, note2)
    )

    AppTheme {
        HomeScreen(state = main, {}, {}, {}, {}, {}, {}, {}, {}, {})
    }
}
