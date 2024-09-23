package io.writerme.features.home.presentation.screens.bookmark

import io.writerme.core.models.viewdata.BookmarksFolderViewData
import io.writerme.core.models.viewdata.ComponentViewData

internal sealed class State {
    data class Content(
        val currentFolder: BookmarksFolderViewData? = null,
        val isBookmarkDialogDisplayed: Boolean = false,
        val isFolderDialogDisplayed: Boolean = false,
        val isFloatingDialogShown: Boolean = false
    ) : State()
}

internal sealed class Event {
    data object Init : Event()
    data object OnBack : Event()
    data object OnToggleFloatingDialog : Event()
    data class OnFolderClick(
        val folder: BookmarksFolderViewData
    ) : Event()

    data class OnLinkClick(
        val link: ComponentViewData
    ) : Event()

    data object OnToggleCreateFolderDialog : Event()
    data object OnToggleCreateBookmarkDialog : Event()
    data object OnNavigateToParentFolder : Event()
    data class OnCreateFolder(val name: String) : Event()
    data class OnCreateBookmark(
        val url: String,
        val title: String,
        val parent: BookmarksFolderViewData? = null
    ) : Event()

    data class OnDeleteFolder(
        val folder: BookmarksFolderViewData
    ) : Event()

    data class OnDeleteBookmark(
        val bookmark: ComponentViewData
    ) : Event()
}

internal sealed class Action {
    data object Init : Action()
    data object NetworkError : Action()
    data class Error(val message: String? = null) : Action()
    data object Back : Action()
    data class OpenOnWeb(
        val url: String,
        val openInIncognitoMode: Boolean = false
    ) : Action()
}
