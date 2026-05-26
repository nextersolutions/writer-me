package io.writerme.app.ui.screen

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.R
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.data.viewdata.NoteViewData
import io.writerme.app.ui.component.AddLinkDialogBody
import io.writerme.app.ui.component.Checkbox
import io.writerme.app.ui.component.Link
import io.writerme.app.ui.component.NoteText
import io.writerme.app.ui.component.TagsBar
import io.writerme.app.ui.component.Task
import io.writerme.app.ui.component.TitledDialog
import io.writerme.app.ui.state.NoteState
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.dropdownBackground
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.OnLifecycleEvent
import io.writerme.app.utils.copyComponentContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    noteState: StateFlow<NoteState>,
    toggleHistoryMode: () -> Unit,
    toggleTopBarDropdownVisibility: () -> Unit,
    updateCoverImage: (String) -> Unit,
    showHashtagBar: (Boolean) -> Unit,
    addNewTag: (String) -> Unit,
    deleteTag: (String) -> Unit,
    modifyHistory: (Long, ComponentViewData) -> Unit,
    saveChanges: () -> Unit,
    onComponentChange: (ComponentViewData) -> Unit,
    addNewCheckBox: (Int) -> Unit,
    navigateBack: () -> Unit,
    addImageSection: (String) -> Unit,
    showDropdown: (Int) -> Unit,
    dismissDropDown: () -> Unit,
    toggleDropDownHistoryMode: () -> Unit,
    addLinkSection: (String) -> Unit,
    toggleCheckbox: (ComponentViewData) -> Unit,
    deleteSection: (Long) -> Unit,
    toggleAddLinkDialogVisibility: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val focusRequester = remember { FocusRequester() }

    val state = noteState.collectAsStateWithLifecycle()

    OnLifecycleEvent(onEvent = { _, event ->
        if (event == Lifecycle.Event.ON_PAUSE) {
            saveChanges()
        }
    })

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
                    elevation = 0.dp,
                    modifier = Modifier.statusBarsPadding(),
                    title = {
                        Text(
                            text = stringResource(id = R.string.edit),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = toggleTopBarDropdownVisibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more),
                                contentDescription = stringResource(id = R.string.more),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        MaterialTheme(
                            colors = MaterialTheme.colors.copy(
                                surface = MaterialTheme.colors.dropdownBackground,
                                background = Color.Blue
                            ),
                            shapes = MaterialTheme.shapes.copy(
                                medium = RoundedCornerShape(
                                    dimensionResource(id = R.dimen.small_radius)
                                )
                            )
                        ) {
                            DropdownMenu(
                                expanded = state.value.isTopBarDropdownVisible,
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
                                        color = MaterialTheme.colors.light
                                    )

                                    Spacer(modifier = Modifier.width(width = 8.dp))

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_history),
                                        contentDescription = stringResource(id = R.string.history_mode),
                                        tint = MaterialTheme.colors.light
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
                        .navigationBarsPadding()
                        .clip(
                            RoundedCornerShape(
                                dimensionResource(id = R.dimen.big_radius)
                            )
                        ),
                    backgroundColor = MaterialTheme.colors.backgroundGrey,
                    elevation = dimensionResource(id = R.dimen.shadow)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (!state.value.isTagsBarVisible) {
                                showHashtagBar(true)
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_hashtag),
                                contentDescription = stringResource(id = R.string.add_hashtag_button),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = toggleAddLinkDialogVisibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_link),
                                contentDescription = stringResource(id = R.string.add_link_button),
                                tint = MaterialTheme.colors.light
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
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = { addNewCheckBox(-1) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_checked),
                                contentDescription = stringResource(id = R.string.add_tast_button),
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = { addImageComponentPicker.launch("image/*") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = stringResource(id = R.string.add_media_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            val note = state.value.note
            val padding = dimensionResource(id = R.dimen.screen_padding)

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = padding, top = padding, end = padding,
                        bottom = paddingValues.calculateBottomPadding() + padding
                    )
                    .imePadding()
            ) {
                item {
                    val title = note.title?.newest()

                    if (note.cover?.isNotEmpty() == true) {
                        val image = note.cover?.newest()

                        Column(
                            modifier = Modifier.padding(bottom = padding)
                        ) {
                            image?.let {
                                io.writerme.app.ui.component.Image(
                                    component = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = padding)
                                        .clickable { addCoverImagePicker.launch("image/*") }
                                )
                            }

                            NoteText(
                                component = title ?: ComponentViewData.empty(),
                                onValueChange = onComponentChange,
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
                                    .background(MaterialTheme.colors.backgroundGrey)
                                    .padding(4.dp, 12.dp)
                                    .shadow(dimensionResource(id = R.dimen.shadow), shape),
                                onClick = { addCoverImagePicker.launch("image/*") }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_camera),
                                    contentDescription = stringResource(id = R.string.add_cover_image_button),
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colors.light
                                )
                            }

                            NoteText(
                                component = title ?: ComponentViewData.empty(),
                                onValueChange = onComponentChange,
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
                    AnimatedVisibility(visible = state.value.note.tags.isNotEmpty()) {
                        Column {
                            TagsBar(
                                tags = state.value.note.tags,
                                addNewTag = addNewTag,
                                deleteTag = deleteTag,
                                focusRequester = focusRequester
                            )

                            Spacer(modifier = Modifier.height(padding))
                        }
                    }
                }

                itemsIndexed(
                    items = note.content,
                    itemContent = { currentIndex, item ->
                        val newest = item.newest()

                        newest?.let { component ->
                            val isExpanded = currentIndex == state.value.expandedDropdownId

                            ExposedDropdownMenuBox(
                                expanded = isExpanded,
                                onExpandedChange = { dismissDropDown() }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AnimatedVisibility(
                                        visible = state.value.isHistoryMode,
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_more),
                                            contentDescription = stringResource(id = R.string.more),
                                            tint = MaterialTheme.colors.light,
                                            modifier = Modifier
                                                .padding(0.dp, 0.dp, 0.dp, 8.dp)
                                                .size(20.dp)
                                                .clickable { showDropdown(currentIndex) }
                                        )
                                    }

                                    when (component.type) {
                                        ComponentType.Text -> {
                                            NoteText(
                                                component = component,
                                                onValueChange = onComponentChange
                                            )
                                        }

                                        ComponentType.Checkbox -> {
                                            Checkbox(
                                                component = component,
                                                modifier = Modifier.padding(start = padding),
                                                onValueChange = onComponentChange,
                                                onCheckedChange = { toggleCheckbox(component) },
                                                onAddNewCheckbox = { addNewCheckBox(currentIndex) },
                                                onDeleteCheckBox = { deleteSection(item.id) },
                                            )
                                        }

                                        ComponentType.Voice -> {}
                                        ComponentType.Task -> {
                                            Task(
                                                task = component,
                                                onClick = { /*TODO*/ },
                                                onValueChange = onComponentChange
                                            )
                                        }

                                        ComponentType.Link -> {
                                            Link(
                                                link = component,
                                                onClick = {},
                                                onLongClick = {}
                                            )
                                        }

                                        ComponentType.Video -> {
                                            // pending feature
                                        }

                                        ComponentType.Image -> {
                                            io.writerme.app.ui.component.Image(
                                                component = component
                                            )
                                        }
                                    }
                                }

                                MaterialTheme(
                                    colors = MaterialTheme.colors.copy(
                                        surface = MaterialTheme.colors.light,
                                        background = Color.Blue
                                    ),
                                    shapes = MaterialTheme.shapes.copy(
                                        medium = RoundedCornerShape(
                                            dimensionResource(id = R.dimen.small_radius)
                                        )
                                    )
                                ) {
                                    ExposedDropdownMenu(
                                        expanded = isExpanded,
                                        onDismissRequest = { dismissDropDown() },
                                        scrollState = rememberScrollState()
                                    ) {
                                        if (state.value.isDropDownInHistoryMode) {
                                            item.changes.forEach { historyComponent ->
                                                DropdownMenuItem(onClick = {
                                                    modifyHistory(item.id, historyComponent)
                                                    dismissDropDown()
                                                }) {
                                                    when (historyComponent.type) {
                                                        ComponentType.Text,
                                                        ComponentType.Checkbox,
                                                        ComponentType.Task -> {
                                                            Text(
                                                                text = historyComponent.content,
                                                                style = MaterialTheme.typography.body1
                                                            )
                                                        }

                                                        ComponentType.Link -> {
                                                            Text(
                                                                text = historyComponent.url,
                                                                style = MaterialTheme.typography.body1,
                                                                modifier = Modifier.fillMaxWidth(),
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                        }

                                                        ComponentType.Image -> {
                                                            io.writerme.app.ui.component.Image(
                                                                component = historyComponent
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
                                                        component = component, context = context
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
                                                            modifier = Modifier.size(20.dp),
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
                                                            modifier = Modifier.size(20.dp),
                                                            tint = Color.DarkGray
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(padding))
                        }
                    }
                )
            }
        }

        if (state.value.isAddLinkDialogDisplayed) {
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
    val noteState = NoteState(note = NoteViewData.empty())
    val state = MutableStateFlow(noteState)

    WriterMeTheme {
        NoteScreen(
            noteState = state, {}, {}, {}, {}, {}, {},
            { _, _ -> }, {}, {}, { _ -> }, {}, { _ -> }, {}, {}, {}, {}, {}, {}, {})
    }
}
