package io.writerme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks_folders")
data class BookmarksFolderEntity(
    @PrimaryKey val id: Long = System.currentTimeMillis(),
    val name: String = "",
    val parentId: Long? = null,
    val changeTime: Long = System.currentTimeMillis()
)
