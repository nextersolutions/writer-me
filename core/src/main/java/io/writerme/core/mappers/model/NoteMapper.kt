package io.writerme.core.mappers.model

import io.writerme.core.extensions.toRealmList
import io.writerme.core.mappers.IBaseEntityMapper
import io.writerme.core.mappers.IBaseMapper
import io.writerme.core.models.model.Note
import io.writerme.core.models.viewdata.NoteViewData
import javax.inject.Inject

class NoteMapper @Inject constructor(
    private val historyMapper: HistoryMapper
) : IBaseEntityMapper<Note, NoteViewData>, IBaseMapper<NoteViewData, Note> {
    override fun toEntity(model: NoteViewData): Note {
        return Note().apply {
            id = model.id
            title = if (model.title != null) historyMapper.toEntity(model.title) else null
            cover = if (model.cover != null) historyMapper.toEntity(model.cover) else null
            content = model.content.map { historyMapper.toEntity(it) }.toRealmList()
            isImportant = model.isImportant
            createdAt = model.createdAt
            changeTime = model.changeTime
            tags = model.tags.toRealmList()
        }
    }

    override fun toDomain(model: Note): NoteViewData {
        return NoteViewData(
            id = model.id,
            title = if (model.title != null) historyMapper.toDomain(model.title!!) else null,
            cover = if (model.cover != null) historyMapper.toDomain(model.cover!!) else null,
            content = model.content.toList().map { historyMapper.toDomain(it) },
            isImportant = model.isImportant,
            createdAt = model.createdAt,
            changeTime = model.changeTime,
            tags = model.tags.toList()
        )
    }
}
