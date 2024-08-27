package io.writerme.features.home.models

import io.writerme.core.models.viewdata.NoteViewData

data class NoteScreenState(
    val note: NoteViewData = NoteViewData(),
    val isTagsBarVisible: Boolean = false,
    val isTopBarDropdownVisible: Boolean = false,
    val isDropDownInHistoryMode: Boolean = false,
    val isAddLinkDialogDisplayed: Boolean = false,
    val isHistoryMode: Boolean = false
)
