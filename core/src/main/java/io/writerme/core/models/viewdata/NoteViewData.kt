package io.writerme.core.models.viewdata

import io.writerme.core.common.FormatUtils.EMPTY
import java.util.Date

data class NoteViewData(
    val id: String = EMPTY,
    val title: HistoryViewData? = null,
    val cover: HistoryViewData? = null,
    var content: List<HistoryViewData> = emptyList(),
    val isImportant: Boolean = false,
    val createdAt: Date = Date(),
    val changeTime: Long = System.currentTimeMillis(),
    val tags: List<String> = emptyList()
)
