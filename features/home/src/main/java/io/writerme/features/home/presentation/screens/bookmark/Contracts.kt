package io.writerme.features.home.presentation.screens.bookmark

import io.writerme.core.models.viewdata.BookmarksFolderViewData

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
}

internal sealed class Action {
    data object Init : Action()
    data object NetworkError : Action()
    data class Error(val message: String? = null) : Action()
}
