package io.writerme.app.data.viewdata

import java.util.Date

data class NoteViewData(
    val id: Long,
    val title: HistoryViewData?,
    val cover: HistoryViewData?,
    val content: List<HistoryViewData>,
    val isImportant: Boolean,
    val created: Date,
    val changeTime: Long,
    val tags: List<String>
) {
    companion object {
        fun empty(): NoteViewData = NoteViewData(
            id = 0,
            title = HistoryViewData.empty(),
            cover = HistoryViewData.empty(),
            content = emptyList(),
            isImportant = false,
            created = Date(),
            changeTime = System.currentTimeMillis(),
            tags = emptyList()
        )
    }
}
