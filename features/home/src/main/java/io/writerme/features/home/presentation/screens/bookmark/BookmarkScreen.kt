package io.writerme.features.home.presentation.screens.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import io.writerme.features.home.presentation.component.BookmarksComponent
import io.writerme.resources.common.BaseScreen
import kotlinx.coroutines.flow.collectLatest
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Immutable
data object BookmarksScreen : BaseScreen() {
    @Composable
    override fun Content() {
        val navigator = LocalRootController.current
        val viewModel = viewModel<BookmarksViewModel>()
        val state by viewModel.state.collectAsState()

        when (state) {
            is State.Content -> {
                val st = state as State.Content

                if (st.currentFolder != null) {
                    BookmarksComponent(
                        currentFolder = st.currentFolder,
                        isFloatingDialogShown = st.isFloatingDialogShown,
                        isBookmarkDialogDisplayed = st.isBookmarkDialogDisplayed,
                        isFolderDialogDisplayed = st.isFolderDialogDisplayed,
                        onFolderClicked = {
                            viewModel.obtainEvent(
                                Event.OnFolderClick(it)
                            )
                        },
                        onLinkClicked = {
                            viewModel.obtainEvent(
                                Event.OnLinkClick(it)
                            )
                        },
                        toggleCreateFolderDialog = {
                            viewModel.obtainEvent(Event.OnToggleCreateFolderDialog)
                        },
                        toggleCreateBookmarkDialog = {
                            viewModel.obtainEvent(Event.OnToggleCreateBookmarkDialog)
                        },
                        toggleFloatingDialog = {
                            viewModel.obtainEvent(Event.OnToggleFloatingDialog)
                        },
                        navigateToParentFolder = {
                            viewModel.obtainEvent(Event.OnNavigateToParentFolder)
                        },
                        createBookmark = { url, title, parent ->
                            viewModel.obtainEvent(
                                Event.OnCreateBookmark(url, title, parent)
                            )
                        },
                        createFolder = {
                            viewModel.obtainEvent(
                                Event.OnCreateFolder(it)
                            )
                        },
                        onDeleteFolder = {
                            viewModel.obtainEvent(
                                Event.OnDeleteFolder(it)
                            )
                        },
                        onDeleteBookmark = {
                            viewModel.obtainEvent(
                                Event.OnDeleteBookmark(it)
                            )
                        },
                        dismissScreen = {
                            viewModel.obtainEvent(Event.OnBack)
                        }
                    )
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            with(viewModel) {
                viewModel.obtainEvent(Event.Init)
                action.collectLatest { action ->
                    when (action) {
                        is Action.Init -> {}
                        Action.Back -> TODO()
                        is Action.Error -> TODO()
                        Action.NetworkError -> TODO()
                        is Action.OpenOnWeb -> TODO()
                    }
                }
            }
        }
    }
}
