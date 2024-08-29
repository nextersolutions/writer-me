package io.writerme.features.home.presentation.component

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.PopupProperties
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.core.models.viewdata.HistoryViewData
import io.writerme.core.models.viewdata.NoteViewData
import io.writerme.features.home.models.NoteScreenState
import io.writerme.resources.R
import io.writerme.resources.common.Dimens.GRID_0
import io.writerme.resources.common.Dimens.GRID_12
import io.writerme.resources.common.Dimens.GRID_20
import io.writerme.resources.common.Dimens.GRID_24
import io.writerme.resources.common.Dimens.GRID_4
import io.writerme.resources.common.Dimens.GRID_40
import io.writerme.resources.common.Dimens.GRID_8
import io.writerme.resources.common.Dimens.bigRadius
import io.writerme.resources.common.Dimens.screenPadding
import io.writerme.resources.common.Dimens.shadowRadius
import io.writerme.resources.common.Dimens.smallRadius
import io.writerme.resources.extensions.copyComponentContent
import io.writerme.resources.themes.AppTheme
import io.writerme.resources.themes.WriterMeTheme
import io.writerme.resources.widgets.Bookmarks.Link
import io.writerme.resources.widgets.Cards.NoteText
import io.writerme.resources.widgets.Checkbox
import io.writerme.resources.widgets.Dialogs.AddLinkDialogBody
import io.writerme.resources.widgets.Dialogs.TitledDialog
import io.writerme.resources.widgets.Spacers.SpacerHorizontal
import io.writerme.resources.widgets.Spacers.SpacerVertical
import io.writerme.resources.widgets.TagsBar
import io.writerme.resources.widgets.Task
import java.util.Date

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteComponent(
    state: NoteScreenState,
    toggleHistoryMode: () -> Unit,
    toggleTopBarDropdownVisibility: () -> Unit,
    updateCoverImage: (String) -> Unit,
    showHashtagBar: (Boolean) -> Unit,
    addNewTag: (String) -> Unit,
    deleteTag: (String) -> Unit,
    saveChanges: () -> Unit,
    onComponentChange: (HistoryViewData, ComponentViewData) -> Unit,
    addNewCheckBox: (Int) -> Unit,
    onBack: () -> Unit,
    addImageSection: (String) -> Unit,
    showDropdown: (Int) -> Unit,
    dismissDropDown: () -> Unit,
    toggleDropDownHistoryMode: () -> Unit,
    addLinkSection: (String) -> Unit,
    toggleCheckbox: (ComponentViewData) -> Unit,
    deleteSection: (HistoryViewData) -> Unit,
    toggleAddLinkDialogVisibility: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    val focusRequester = remember { FocusRequester() }

    /*OnLifecycleEvent(onEvent = { _, event ->
        if (event == Lifecycle.Event.ON_PAUSE) {
            saveChanges()
        }
    })*/

    val context = LocalContext.current

    val addCoverImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { updateCoverImage(it.toString()) }
        }
    )

    val addImageComponentPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { addImageSection(it.toString()) }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
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
                    title = {
                        Text(
                            text = stringResource(id = R.string.edit),
                            style = MaterialTheme.typography.h2,
                            color = WriterMeTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = WriterMeTheme.colors.light
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = toggleTopBarDropdownVisibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more),
                                contentDescription = stringResource(id = R.string.more),
                                tint = WriterMeTheme.colors.light
                            )
                        }

                        MaterialTheme(
                            colors = MaterialTheme.colors.copy(
                                surface = WriterMeTheme.colors.dropdownBackground,
                                background = Color.Blue
                            ),
                            shapes = MaterialTheme.shapes.copy(
                                medium = RoundedCornerShape(smallRadius)
                            )
                        ) {
                            DropdownMenu(
                                expanded = state.isTopBarDropdownVisible,
                                onDismissRequest = toggleTopBarDropdownVisibility,
                                properties = PopupProperties()
                            ) {
                                DropdownMenuItem(
                                    onClick = toggleHistoryMode,
                                    enabled = true
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.history_mode),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = WriterMeTheme.colors.light
                                    )

                                    SpacerHorizontal(GRID_8)

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_history),
                                        contentDescription = stringResource(id = R.string.history_mode),
                                        tint = WriterMeTheme.colors.light
                                    )
                                }
                            }
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(bigRadius)
                        ),
                    backgroundColor = WriterMeTheme.colors.backgroundGrey,
                    elevation = shadowRadius
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*
                        // pending features

                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bold),
                                contentDescription = stringResource(id = R.string.bold_icon)
                            )
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_italic),
                                contentDescription = stringResource(id = R.string.italic_icon)
                            )
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_underline),
                                contentDescription = stringResource(id = R.string.underline_icon)
                            )
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_spacing),
                                contentDescription = stringResource(id = R.string.spacing_icon)
                            )
                        }

                        */

                        IconButton(onClick = {
                            if (!state.isTagsBarVisible) {
                                showHashtagBar(true)
                            }
                            // focusRequester.requestFocus()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_hashtag),
                                contentDescription = stringResource(id = R.string.add_hashtag_button),
                                tint = WriterMeTheme.colors.light
                            )
                        }

                        IconButton(onClick = toggleAddLinkDialogVisibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_link),
                                contentDescription = stringResource(id = R.string.add_link_button),
                                tint = WriterMeTheme.colors.light
                            )
                        }

                        IconButton(onClick = {
                            // pending: add voice
                            Toast.makeText(
                                context,
                                context.resources.getString(R.string.pending),
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_microphone),
                                contentDescription = stringResource(id = R.string.add_voice_note_button),
                                tint = WriterMeTheme.colors.light
                            )
                        }

                        IconButton(onClick = {
                            addNewCheckBox(-1)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_checked),
                                contentDescription = stringResource(id = R.string.add_tast_button),
                                modifier = Modifier.size(GRID_24),
                                tint = WriterMeTheme.colors.light
                            )
                        }

                        IconButton(onClick = {
                            addImageComponentPicker.launch("image/*")
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = stringResource(id = R.string.add_media_button),
                                tint = WriterMeTheme.colors.light
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            val note = state.note
            val padding = screenPadding

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = padding,
                        top = padding,
                        end = padding,
                        bottom = paddingValues.calculateBottomPadding() + padding
                    )
                    .systemBarsPadding()
                    .imePadding()
            ) {
                item {
                    val title = note.title?.newest()

                    if (note.cover != null && note.cover!!.isNotEmpty()) {
                        val image = note.cover!!.newest()

                        Column(
                            modifier = Modifier.padding(bottom = padding)
                        ) {
                            image?.let {
                                io.writerme.resources.widgets.Images.Image(
                                    component = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = padding)
                                        .clickable {
                                            addCoverImagePicker.launch("image/*")
                                        }
                                )
                            }

                            NoteText(
                                component = title ?: ComponentViewData(),
                                onValueChange = { component ->
                                    note.title?.let { history ->
                                        onComponentChange(history, component)
                                    }
                                },
                                typography = MaterialTheme.typography.h1,
                                placeholderResource = R.string.type_title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = dimensionResource(id = R.dimen.screen_padding_big))
                            )
                        }
                    } else {
                        val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = padding)
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .clip(shape)
                                    .background(WriterMeTheme.colors.backgroundGrey)
                                    .padding(GRID_4, GRID_12)
                                    .shadow(dimensionResource(id = R.dimen.shadow), shape),
                                onClick = {
                                    addCoverImagePicker.launch("image/*")
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_camera),
                                    contentDescription = stringResource(id = R.string.add_cover_image_button),
                                    modifier = Modifier.size(GRID_40),
                                    tint = WriterMeTheme.colors.light
                                )
                            }

                            NoteText(
                                component = title ?: ComponentViewData(),
                                onValueChange = { component ->
                                    note.title?.let { history ->
                                        onComponentChange(history, component)
                                    }
                                },
                                typography = MaterialTheme.typography.h1,
                                placeholderResource = R.string.type_title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = padding)
                            )
                        }
                    }
                }

                item {
                    AnimatedVisibility(visible = state.note.tags.isNotEmpty()) {
                        Column {
                            TagsBar(
                                tags = state.note.tags,
                                addNewTag = addNewTag,
                                deleteTag = deleteTag,
                                focusRequester = focusRequester
                            )

                            SpacerVertical(padding)
                        }
                    }
                }

                itemsIndexed(
                    items = note.content,
                    itemContent = { currentIndex, item ->
                        val newest = item.newest()

                        newest?.let { component ->
                            val isExpanded = false // currentIndex == state.expandedDropdownId

                            ExposedDropdownMenuBox(
                                expanded = isExpanded,
                                onExpandedChange = { dismissDropDown() }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AnimatedVisibility(
                                        visible = state.isHistoryMode,
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_more),
                                            contentDescription = stringResource(id = R.string.more),
                                            tint = WriterMeTheme.colors.light,
                                            modifier = Modifier
                                                .padding(bottom = GRID_8)
                                                .size(GRID_20)
                                                .clickable {
                                                    showDropdown(currentIndex)
                                                }
                                        )
                                    }

                                    when (component.type) {
                                        ComponentType.Text -> {
                                            NoteText(
                                                component = component,
                                                onValueChange = {
                                                    onComponentChange(item, component)
                                                }
                                            )
                                        }

                                        ComponentType.Checkbox -> {
                                            Checkbox(
                                                component = component,
                                                modifier = Modifier
                                                    .animateItemPlacement()
                                                    .padding(start = padding),
                                                onValueChange = {
                                                    onComponentChange(item, component)
                                                },
                                                onCheckedChange = {
                                                    toggleCheckbox(component)
                                                },
                                                onAddNewCheckbox = {
                                                    addNewCheckBox(currentIndex)
                                                },
                                                onDeleteCheckBox = {
                                                    deleteSection(item)
                                                }
                                            )
                                        }

                                        ComponentType.Voice -> {}
                                        ComponentType.Task -> {
                                            Task(
                                                task = component,
                                                onClick = { /*TODO*/ },
                                                onValueChange = {
                                                    onComponentChange(item, component)
                                                }
                                            )
                                        }

                                        ComponentType.Link -> {
                                            Link(
                                                link = component,
                                                onClick = {},
                                                onLongClick = {}
                                            )
                                            // TODO: link is not editable, though it should be
                                        }

                                        ComponentType.Video -> {
                                            // pending feature
                                        }

                                        ComponentType.Image -> {
                                            io.writerme.resources.widgets.Images.Image(
                                                component = component
                                            )
                                        }
                                    }
                                }

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
                                        onDismissRequest = { dismissDropDown() },
                                        scrollState = rememberScrollState()
                                    ) {
                                        if (state.isDropDownInHistoryMode) {
                                            item.changes.forEach { component ->
                                                DropdownMenuItem(onClick = {
                                                    onComponentChange(item, component)
                                                    dismissDropDown()
                                                }) {
                                                    when (component.type) {
                                                        ComponentType.Text,
                                                        ComponentType.Checkbox,
                                                        ComponentType.Task -> {
                                                            Text(
                                                                text = component.content,
                                                                style = MaterialTheme.typography.body1
                                                            )
                                                        }

                                                        ComponentType.Link -> {
                                                            Text(
                                                                text = component.url,
                                                                style = MaterialTheme.typography.body1,
                                                                modifier = Modifier.fillMaxWidth(),
                                                                maxLines = VALUE_1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                        }

                                                        ComponentType.Image -> {
                                                            io.writerme.resources.widgets.Images.Image(
                                                                component = component
                                                            )
                                                        }

                                                        ComponentType.Voice -> {
                                                            // pending
                                                        }

                                                        ComponentType.Video -> {
                                                            // pending feature
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            val clipboardManager = LocalClipboardManager.current

                                            Column {
                                                DropdownMenuItem(onClick = {
                                                    clipboardManager.copyComponentContent(
                                                        component = component,
                                                        context = context
                                                    )
                                                    dismissDropDown()
                                                }) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = stringResource(id = R.string.copy),
                                                            style = MaterialTheme.typography.body1
                                                        )

                                                        Icon(
                                                            painter = painterResource(id = R.drawable.ic_history),
                                                            contentDescription = stringResource(id = R.string.copy),
                                                            modifier = Modifier.size(GRID_20),
                                                            tint = Color.DarkGray
                                                        )
                                                    }
                                                }

                                                DropdownMenuItem(onClick = toggleDropDownHistoryMode) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = stringResource(id = R.string.history),
                                                            style = MaterialTheme.typography.body1
                                                        )

                                                        Icon(
                                                            painter = painterResource(id = R.drawable.ic_history),
                                                            contentDescription = stringResource(id = R.string.copy),
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

                            SpacerVertical(padding)
                        }
                    }
                )
            }
        }

        if (state.isAddLinkDialogDisplayed) {
            TitledDialog(
                title = stringResource(id = R.string.set_link),
                onDismiss = toggleAddLinkDialogVisibility,
                content = {
                    AddLinkDialogBody(
                        addLink = addLinkSection,
                        onDismiss = toggleAddLinkDialogVisibility
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun NoteScreenPreview() {
    val text = ComponentViewData(
        type = ComponentType.Text,
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry..."
    )

    val checkbox = ComponentViewData(
        type = ComponentType.Checkbox,
        content = "Complete writing post for Instagram",
        isChecked = true
    )

    val checkbox1 = ComponentViewData(
        type = ComponentType.Checkbox,
        content = "Complete writing post for Instagram",
        isChecked = true
    )

    val checkbox2 = ComponentViewData(
        type = ComponentType.Checkbox,
        content = "Complete writing post for Instagram",
        isChecked = true
    )

    val task = ComponentViewData(
        content = "Meeting with Anna",
        time = Date(),
        type = ComponentType.Task
    )

    val image = ComponentViewData(
        type = ComponentType.Image,
        mediaUrl = ""
    )

    val link = ComponentViewData(
        type = ComponentType.Link,
        title = "Best resort in Italy you ever dreamed about",
        url = ""
    )

    val note = NoteViewData(
        title = HistoryViewData(
            changes = listOf(
                ComponentViewData(
                    title = "Instagram Content Plan for Beginner",
                    type = ComponentType.Text
                )
            )
        ),

        content = listOf(
            HistoryViewData(
                changes = listOf(text)
            ),
            HistoryViewData(
                changes = listOf(checkbox)
            ),
            HistoryViewData(
                changes = listOf(checkbox1)
            ),
            HistoryViewData(
                changes = listOf(checkbox2)
            )
        ),
        tags = listOf("stories", "work")
    )

    val noteScreenState = NoteScreenState(note = note)

    AppTheme {
        NoteComponent(
            state = noteScreenState,
            toggleHistoryMode = {},
            toggleTopBarDropdownVisibility = {},
            updateCoverImage = {},
            showHashtagBar = {},
            addNewTag = {},
            deleteTag = {},
            saveChanges = {},
            onComponentChange = { _, _ -> },
            addNewCheckBox = {},
            onBack = {},
            addImageSection = {},
            showDropdown = {},
            dismissDropDown = {},
            toggleDropDownHistoryMode = {},
            addLinkSection = {},
            toggleCheckbox = {},
            deleteSection = {},
            toggleAddLinkDialogVisibility = {}
        )
    }
}
