package io.writerme.app.data.model

import androidx.room.Entity

@Entity(
    tableName = "note_tags",
    primaryKeys = ["noteId", "tag"]
)
data class NoteTagEntity(
    val noteId: Long,
    val tag: String
)
