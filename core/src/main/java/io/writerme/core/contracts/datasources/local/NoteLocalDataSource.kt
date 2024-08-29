package io.writerme.core.contracts.datasources.local

import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.models.model.Component
import io.writerme.core.models.model.History
import io.writerme.core.models.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    suspend fun createNewNote(): Flow<ObjectChange<Note>>
    fun getNotes(): Flow<ResultsChange<Note>>
    suspend fun getNote(noteId: String): Flow<ObjectChange<Note>>
    suspend fun saveComponent(component: Component): Component
    suspend fun updateHistory(historyId: String, component: Component)
    suspend fun updateNoteCoverImage(noteId: String, uri: String?)
    suspend fun addNewTag(noteId: String, tag: String)
    suspend fun deleteTag(noteId: String, tag: String)
    suspend fun addNewCheckBox(noteId: String, currentPosition: Int = -VALUE_1)
    suspend fun addSection(noteId: String, comp: Component)
    suspend fun toggleImportance(noteId: String)
    suspend fun deleteNote(noteId: String)
    suspend fun toggleCheckbox(component: Component)
    suspend fun deleteSection(hist: History)
}