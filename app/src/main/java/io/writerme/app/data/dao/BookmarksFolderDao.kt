package io.writerme.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.writerme.app.data.model.BookmarksFolderEntity
import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.model.FolderBookmarkCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksFolderDao {

    @Query("SELECT * FROM bookmarks_folders WHERE id = :id")
    suspend fun getById(id: Long): BookmarksFolderEntity?

    @Query("SELECT * FROM bookmarks_folders WHERE id = :id")
    fun observeById(id: Long): Flow<BookmarksFolderEntity?>

    @Query("SELECT * FROM bookmarks_folders WHERE parentId = :parentId")
    suspend fun getSubfolders(parentId: Long): List<BookmarksFolderEntity>

    @Query(
        """SELECT c.* FROM components c
           INNER JOIN folder_bookmarks fb ON c.id = fb.componentId
           WHERE fb.folderId = :folderId"""
    )
    suspend fun getBookmarks(folderId: Long): List<ComponentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: BookmarksFolderEntity)

    @Update
    suspend fun update(folder: BookmarksFolderEntity)

    @Query("DELETE FROM bookmarks_folders WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkCrossRef(crossRef: FolderBookmarkCrossRef)

    @Query("DELETE FROM folder_bookmarks WHERE folderId = :folderId AND componentId = :componentId")
    suspend fun deleteBookmarkCrossRef(folderId: Long, componentId: Long)

    @Query("DELETE FROM folder_bookmarks WHERE componentId = :componentId")
    suspend fun deleteAllCrossRefsForComponent(componentId: Long)

    @Query("UPDATE bookmarks_folders SET changeTime = :changeTime WHERE id = :folderId")
    suspend fun updateChangeTime(folderId: Long, changeTime: Long)
}
