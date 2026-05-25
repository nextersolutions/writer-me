package io.writerme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: Long = System.currentTimeMillis(),
    val titleHistoryId: Long? = null,
    val coverHistoryId: Long? = null,
    val isImportant: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    val changeTime: Long = System.currentTimeMillis()
)
