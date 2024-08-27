package io.writerme.core.models.viewdata

import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.core.common.FormatUtils.EMPTY

data class HomeViewData(
    val firstName: String = EMPTY,
    val profilePhotoUrl: String = EMPTY,
    val chosenTab: HomeFilterTab = HomeFilterTab.All,
    val isSearchMode: Boolean = false,
    val isImportantVisible: Boolean = false,
    var notes: List<NoteViewData> = emptyList()
)
