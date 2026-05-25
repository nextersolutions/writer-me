package io.writerme.app.data.mapper

import io.writerme.app.data.model.NoteEntity
import io.writerme.app.data.viewdata.HistoryViewData
import io.writerme.app.data.viewdata.NoteViewData
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteMapper @Inject constructor() {

    fun map(
        entity: NoteEntity,
        tags: List<String>,
        title: HistoryViewData?,
        cover: HistoryViewData?,
        content: List<HistoryViewData>
    ): NoteViewData = NoteViewData(
        id = entity.id,
        title = title,
        cover = cover,
        content = content,
        isImportant = entity.isImportant,
        created = Date(entity.created),
        changeTime = entity.changeTime,
        tags = tags
    )
}
