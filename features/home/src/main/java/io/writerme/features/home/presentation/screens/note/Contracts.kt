package io.writerme.features.home.presentation.screens.note

import io.writerme.core.models.viewdata.NoteViewData

internal sealed class State {
    data class Content(
        val note: NoteViewData? = null,
        val isTagsBarVisible: Boolean = false,
        val isTopBarDropdownVisible: Boolean = false,
        val isDropDownInHistoryMode: Boolean = false,
        val isAddLinkDialogDisplayed: Boolean = false,
        val expandedDropdownId: Int = -1,
        val isHistoryMode: Boolean = false
    ) : State()
}

internal sealed class Event {
    data object Init : Event()
}

internal sealed class Action {
    data object Init : Action()
    data object OnNetworkError : Action()
    data class OnError(val message: String? = null) : Action()
}
