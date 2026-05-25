package io.writerme.app.data.model

import androidx.room.Entity

@Entity(
    tableName = "folder_bookmarks",
    primaryKeys = ["folderId", "componentId"]
)
data class FolderBookmarkCrossRef(
    val folderId: Long,
    val componentId: Long
)
