package io.writerme.app.data.model

import androidx.room.Entity

@Entity(
    tableName = "note_content",
    primaryKeys = ["noteId", "historyId"]
)
data class NoteContentCrossRef(
    val noteId: Long,
    val historyId: Long,
    val position: Int
)
