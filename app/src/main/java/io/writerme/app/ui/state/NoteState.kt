package io.writerme.app.ui.state

import io.writerme.app.data.viewdata.NoteViewData

data class NoteState(
    val note: NoteViewData,
    val isTagsBarVisible: Boolean = false,
    val isTopBarDropdownVisible: Boolean = false,
    val isDropDownInHistoryMode: Boolean = false,
    val isAddLinkDialogDisplayed: Boolean = false,
    val expandedDropdownId: Int = -1,
    val isHistoryMode: Boolean = false
) {
    companion object {
        fun empty(): NoteState = NoteState(NoteViewData.empty())
    }
}
