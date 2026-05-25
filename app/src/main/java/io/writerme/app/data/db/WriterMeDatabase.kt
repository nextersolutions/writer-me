package io.writerme.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.writerme.app.data.dao.BookmarksFolderDao
import io.writerme.app.data.dao.ComponentDao
import io.writerme.app.data.dao.HistoryDao
import io.writerme.app.data.dao.NoteDao
import io.writerme.app.data.dao.SettingsDao
import io.writerme.app.data.model.BookmarksFolderEntity
import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.model.FolderBookmarkCrossRef
import io.writerme.app.data.model.HistoryComponentCrossRef
import io.writerme.app.data.model.HistoryEntity
import io.writerme.app.data.model.NoteContentCrossRef
import io.writerme.app.data.model.NoteEntity
import io.writerme.app.data.model.NoteTagEntity
import io.writerme.app.data.model.SettingsEntity

@Database(
    entities = [
        ComponentEntity::class,
        HistoryEntity::class,
        HistoryComponentCrossRef::class,
        NoteEntity::class,
        NoteContentCrossRef::class,
        NoteTagEntity::class,
        BookmarksFolderEntity::class,
        FolderBookmarkCrossRef::class,
        SettingsEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class WriterMeDatabase : RoomDatabase() {
    abstract fun componentDao(): ComponentDao
    abstract fun historyDao(): HistoryDao
    abstract fun noteDao(): NoteDao
    abstract fun bookmarksFolderDao(): BookmarksFolderDao
    abstract fun settingsDao(): SettingsDao
}
