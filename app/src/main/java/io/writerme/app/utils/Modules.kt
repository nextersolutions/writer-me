package io.writerme.app.utils

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.writerme.app.data.dao.BookmarksFolderDao
import io.writerme.app.data.dao.ComponentDao
import io.writerme.app.data.dao.HistoryDao
import io.writerme.app.data.dao.NoteDao
import io.writerme.app.data.dao.SettingsDao
import io.writerme.app.data.db.WriterMeDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext appContext: Context): WorkManager =
        WorkManager.getInstance(appContext)

    @Provides
    @Singleton
    fun provideFilesUtil(@ApplicationContext appContext: Context): FilesUtil =
        FilesUtil(context = appContext)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): WriterMeDatabase =
        Room.databaseBuilder(appContext, WriterMeDatabase::class.java, Const.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNoteDao(db: WriterMeDatabase): NoteDao = db.noteDao()

    @Provides
    fun provideHistoryDao(db: WriterMeDatabase): HistoryDao = db.historyDao()

    @Provides
    fun provideComponentDao(db: WriterMeDatabase): ComponentDao = db.componentDao()

    @Provides
    fun provideBookmarksFolderDao(db: WriterMeDatabase): BookmarksFolderDao = db.bookmarksFolderDao()

    @Provides
    fun provideSettingsDao(db: WriterMeDatabase): SettingsDao = db.settingsDao()
}
