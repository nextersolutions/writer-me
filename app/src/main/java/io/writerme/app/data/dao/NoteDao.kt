package io.writerme.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.writerme.app.data.model.NoteContentCrossRef
import io.writerme.app.data.model.NoteEntity
import io.writerme.app.data.model.NoteTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY changeTime DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteByIdFlow(id: Long): Flow<NoteEntity?>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE notes SET changeTime = :changeTime WHERE id = :noteId")
    suspend fun updateChangeTime(noteId: Long, changeTime: Long)

    @Query("UPDATE notes SET isImportant = CASE WHEN isImportant = 1 THEN 0 ELSE 1 END WHERE id = :noteId")
    suspend fun toggleImportance(noteId: Long)

    @Query("SELECT tag FROM note_tags WHERE noteId = :noteId")
    suspend fun getTags(noteId: Long): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTag(tag: NoteTagEntity)

    @Query("DELETE FROM note_tags WHERE noteId = :noteId AND tag = :tag")
    suspend fun deleteTag(noteId: Long, tag: String)

    @Query("SELECT historyId FROM note_content WHERE noteId = :noteId ORDER BY position ASC")
    suspend fun getContentHistoryIds(noteId: Long): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentCrossRef(crossRef: NoteContentCrossRef)

    @Query("DELETE FROM note_content WHERE noteId = :noteId AND historyId = :historyId")
    suspend fun deleteContentCrossRef(noteId: Long, historyId: Long)

    @Query("SELECT MAX(position) FROM note_content WHERE noteId = :noteId")
    suspend fun getMaxContentPosition(noteId: Long): Int?

    @Query("SELECT noteId FROM note_content WHERE historyId = :historyId LIMIT 1")
    suspend fun getNoteIdForHistory(historyId: Long): Long?
}
