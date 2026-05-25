package io.writerme.app.data.repository

import io.writerme.app.data.dao.ComponentDao
import io.writerme.app.data.dao.HistoryDao
import io.writerme.app.data.dao.NoteDao
import io.writerme.app.data.mapper.ComponentMapper
import io.writerme.app.data.mapper.HistoryMapper
import io.writerme.app.data.mapper.NoteMapper
import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.HistoryComponentCrossRef
import io.writerme.app.data.model.HistoryEntity
import io.writerme.app.data.model.NoteContentCrossRef
import io.writerme.app.data.model.NoteEntity
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.data.viewdata.NoteViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val historyDao: HistoryDao,
    private val componentDao: ComponentDao,
    private val noteMapper: NoteMapper,
    private val historyMapper: HistoryMapper,
    private val componentMapper: ComponentMapper
) : Repository() {

    fun getNotes(): Flow<List<NoteViewData>> = noteDao.getAllNotes()
        .map { entities -> entities.map { buildNoteViewData(it) } }

    fun getNote(noteId: Long): Flow<NoteViewData> = noteDao.getNoteByIdFlow(noteId)
        .filterNotNull()
        .map { buildNoteViewData(it) }

    suspend fun createNewNote(): Long {
        val noteId = nextId()

        val titleHistoryId = nextId()
        historyDao.insert(HistoryEntity(id = titleHistoryId))
        val titleComponent = ComponentEntity(id = nextId(), noteId = noteId, type = ComponentType.Text.value)
        componentDao.insert(titleComponent)
        historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = titleHistoryId, componentId = titleComponent.id, position = 0))

        val coverHistoryId = nextId()
        historyDao.insert(HistoryEntity(id = coverHistoryId))

        noteDao.insert(
            NoteEntity(
                id = noteId,
                titleHistoryId = titleHistoryId,
                coverHistoryId = coverHistoryId,
                isImportant = false,
                created = System.currentTimeMillis(),
                changeTime = System.currentTimeMillis()
            )
        )

        val textHistoryId = nextId()
        historyDao.insert(HistoryEntity(id = textHistoryId))
        val textComponent = ComponentEntity(id = nextId(), noteId = noteId, type = ComponentType.Text.value)
        componentDao.insert(textComponent)
        historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = textHistoryId, componentId = textComponent.id, position = 0))
        noteDao.insertContentCrossRef(NoteContentCrossRef(noteId = noteId, historyId = textHistoryId, position = 0))

        return noteId
    }

    suspend fun saveComponent(component: ComponentViewData): ComponentViewData {
        componentDao.insert(componentMapper.mapToEntity(component))
        return component
    }

    suspend fun updateHistory(historyId: Long, component: ComponentViewData, maxHistory: Int = 20) {
        val entity = componentMapper.mapToEntity(component)
        componentDao.insert(entity)

        val count = historyDao.getComponentCount(historyId)
        if (count >= maxHistory) {
            val components = historyDao.getComponentsSorted(historyId)
            components.firstOrNull()?.let { oldest ->
                historyDao.deleteCrossRef(historyId, oldest.id)
                componentDao.deleteById(oldest.id)
            }
        }

        val nextPosition = (historyDao.getMaxPosition(historyId) ?: -1) + 1
        historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = historyId, componentId = entity.id, position = nextPosition))

        noteDao.getNoteIdForHistory(historyId)?.let {
            noteDao.updateChangeTime(it, System.currentTimeMillis())
        }
    }

    suspend fun updateNoteCoverImage(noteId: Long, uri: String?) {
        val note = noteDao.getNoteById(noteId) ?: return

        val coverHistoryId = note.coverHistoryId ?: run {
            val newId = nextId()
            historyDao.insert(HistoryEntity(id = newId))
            noteDao.update(note.copy(coverHistoryId = newId, changeTime = System.currentTimeMillis()))
            newId
        }

        val imageComponent = ComponentEntity(
            id = nextId(),
            noteId = noteId,
            mediaUrl = uri,
            type = ComponentType.Image.value
        )
        componentDao.insert(imageComponent)
        val nextPosition = (historyDao.getMaxPosition(coverHistoryId) ?: -1) + 1
        historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = coverHistoryId, componentId = imageComponent.id, position = nextPosition))

        noteDao.updateChangeTime(noteId, System.currentTimeMillis())
    }

    suspend fun addNewTag(noteId: Long, tag: String) {
        val existing = noteDao.getTags(noteId)
        if (!existing.contains(tag)) {
            noteDao.insertTag(io.writerme.app.data.model.NoteTagEntity(noteId = noteId, tag = tag))
        }
        noteDao.updateChangeTime(noteId, System.currentTimeMillis())
    }

    suspend fun deleteTag(noteId: Long, tag: String) {
        noteDao.deleteTag(noteId, tag)
        noteDao.updateChangeTime(noteId, System.currentTimeMillis())
    }

    suspend fun addNewCheckBox(noteId: Long, currentPosition: Int = -1) {
        if (noteId < 0) return

        deleteTextIfNecessary(noteId)

        val checkboxComponent = ComponentEntity(id = nextId(), noteId = noteId, type = ComponentType.Checkbox.value)
        componentDao.insert(checkboxComponent)

        val historyId = nextId()
        historyDao.insert(HistoryEntity(id = historyId))
        historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = historyId, componentId = checkboxComponent.id, position = 0))

        val contentIds = noteDao.getContentHistoryIds(noteId)
        val insertAt = if (currentPosition < 0 || currentPosition + 1 >= contentIds.size) {
            (noteDao.getMaxContentPosition(noteId) ?: -1) + 1
        } else {
            currentPosition + 1
        }
        noteDao.insertContentCrossRef(NoteContentCrossRef(noteId = noteId, historyId = historyId, position = insertAt))

        noteDao.updateChangeTime(noteId, System.currentTimeMillis())

        addTextIfNecessary(noteId)
    }

    suspend fun addSection(noteId: Long, comp: ComponentViewData) {
        if (noteId < 0) return

        deleteTextIfNecessary(noteId)

        val entity = componentMapper.mapToEntity(comp)
        componentDao.insert(entity)

        val historyId = nextId()
        historyDao.insert(HistoryEntity(id = historyId))
        historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = historyId, componentId = entity.id, position = 0))

        val maxPos = (noteDao.getMaxContentPosition(noteId) ?: -1) + 1
        noteDao.insertContentCrossRef(NoteContentCrossRef(noteId = noteId, historyId = historyId, position = maxPos))

        noteDao.updateChangeTime(noteId, System.currentTimeMillis())

        addTextIfNecessary(noteId)
    }

    suspend fun toggleImportance(noteId: Long) {
        if (noteId >= 0) {
            noteDao.toggleImportance(noteId)
            noteDao.updateChangeTime(noteId, System.currentTimeMillis())
        }
    }

    suspend fun deleteNote(noteId: Long) {
        val note = noteDao.getNoteById(noteId) ?: return

        for (historyId in noteDao.getContentHistoryIds(noteId)) {
            deleteHistoryAndComponents(noteId, historyId)
        }

        note.titleHistoryId?.let { hid ->
            deleteHistoryAndComponents(null, hid)
        }
        note.coverHistoryId?.let { hid ->
            historyDao.getComponentsSorted(hid).forEach { c ->
                c.mediaUrl?.let { deleteFile(it) }
                historyDao.deleteCrossRef(hid, c.id)
                componentDao.deleteById(c.id)
            }
            historyDao.deleteById(hid)
        }

        noteDao.deleteById(noteId)
    }

    suspend fun toggleCheckbox(component: ComponentViewData) {
        val entity = componentDao.getById(component.id) ?: return
        componentDao.update(entity.copy(isChecked = !entity.isChecked))
        noteDao.updateChangeTime(component.noteId, System.currentTimeMillis())
    }

    suspend fun deleteSection(histId: Long) {
        val noteId = noteDao.getNoteIdForHistory(histId)
        deleteHistoryAndComponents(noteId, histId)
        noteId?.let { noteDao.updateChangeTime(it, System.currentTimeMillis()) }
    }

    private suspend fun buildNoteViewData(entity: NoteEntity): NoteViewData {
        val tags = noteDao.getTags(entity.id)

        val title = entity.titleHistoryId?.let { hid ->
            historyDao.getById(hid)?.let { historyMapper.map(it, historyDao.getComponentsSorted(hid)) }
        }
        val cover = entity.coverHistoryId?.let { hid ->
            historyDao.getById(hid)?.let { historyMapper.map(it, historyDao.getComponentsSorted(hid)) }
        }
        val content = noteDao.getContentHistoryIds(entity.id).mapNotNull { hid ->
            historyDao.getById(hid)?.let { historyMapper.map(it, historyDao.getComponentsSorted(hid)) }
        }

        return noteMapper.map(entity, tags, title, cover, content)
    }

    private suspend fun addTextIfNecessary(noteId: Long) {
        val contentIds = noteDao.getContentHistoryIds(noteId)
        if (contentIds.isEmpty()) return

        val lastHistoryId = contentIds.last()
        val lastComponent = historyDao.getComponentsSorted(lastHistoryId).lastOrNull()
        val type = lastComponent?.let { ComponentType.fromValue(it.type) }

        if (type == null || type != ComponentType.Text) {
            val textComponent = ComponentEntity(id = nextId(), noteId = noteId, type = ComponentType.Text.value)
            componentDao.insert(textComponent)

            if (type == null) {
                val nextPos = (historyDao.getMaxPosition(lastHistoryId) ?: -1) + 1
                historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = lastHistoryId, componentId = textComponent.id, position = nextPos))
            } else {
                val historyId = nextId()
                historyDao.insert(HistoryEntity(id = historyId))
                historyDao.insertCrossRef(HistoryComponentCrossRef(historyId = historyId, componentId = textComponent.id, position = 0))
                val maxPos = (noteDao.getMaxContentPosition(noteId) ?: -1) + 1
                noteDao.insertContentCrossRef(NoteContentCrossRef(noteId = noteId, historyId = historyId, position = maxPos))
            }

            noteDao.updateChangeTime(noteId, System.currentTimeMillis())
        }
    }

    private suspend fun deleteTextIfNecessary(noteId: Long) {
        val contentIds = noteDao.getContentHistoryIds(noteId)
        if (contentIds.isEmpty()) return

        val lastHistoryId = contentIds.last()
        val components = historyDao.getComponentsSorted(lastHistoryId)
        val lastComponent = components.lastOrNull()

        if (lastComponent != null
            && ComponentType.fromValue(lastComponent.type) == ComponentType.Text
            && lastComponent.content.isEmpty()
        ) {
            deleteHistoryAndComponents(noteId, lastHistoryId)
        }
    }

    private suspend fun deleteHistoryAndComponents(noteId: Long?, historyId: Long) {
        historyDao.getComponentsSorted(historyId).forEach { c ->
            historyDao.deleteCrossRef(historyId, c.id)
            componentDao.deleteById(c.id)
        }
        noteId?.let { noteDao.deleteContentCrossRef(it, historyId) }
        historyDao.deleteById(historyId)
    }

    private fun deleteFile(path: String) {
        try { File(path).delete() } catch (_: Exception) {}
    }
}
