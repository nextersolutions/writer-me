package io.writerme.app.data.repository

import io.writerme.app.data.dao.BookmarksFolderDao
import io.writerme.app.data.dao.ComponentDao
import io.writerme.app.data.mapper.BookmarksFolderMapper
import io.writerme.app.data.mapper.ComponentMapper
import io.writerme.app.data.model.BookmarksFolderEntity
import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.FolderBookmarkCrossRef
import io.writerme.app.data.viewdata.BookmarksFolderViewData
import io.writerme.app.data.viewdata.ComponentViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarksRepository @Inject constructor(
    private val folderDao: BookmarksFolderDao,
    private val componentDao: ComponentDao,
    private val folderMapper: BookmarksFolderMapper,
    private val componentMapper: ComponentMapper
) : Repository() {

    companion object {
        const val ROOT_FOLDER_ID = 0L
    }

    suspend fun ensureRootFolderExists() {
        if (folderDao.getById(ROOT_FOLDER_ID) == null) {
            folderDao.insert(
                BookmarksFolderEntity(
                    id = ROOT_FOLDER_ID,
                    name = "Root",
                    parentId = null,
                    changeTime = System.currentTimeMillis()
                )
            )
        }
    }

    fun observeFolder(folderId: Long): Flow<BookmarksFolderViewData> =
        folderDao.observeById(folderId)
            .filterNotNull()
            .map { buildFolderViewData(it) }

    suspend fun createFolder(name: String, parentId: Long = ROOT_FOLDER_ID) {
        folderDao.insert(
            BookmarksFolderEntity(
                id = nextId(),
                name = name,
                parentId = parentId,
                changeTime = System.currentTimeMillis()
            )
        )
        folderDao.updateChangeTime(parentId, System.currentTimeMillis())
    }

    suspend fun createBookmark(
        url: String,
        title: String,
        parentId: Long = ROOT_FOLDER_ID
    ): ComponentViewData {
        val bookmark = ComponentEntity(
            id = nextId(),
            type = ComponentType.Link.value,
            title = title,
            url = url
        )
        componentDao.insert(bookmark)
        folderDao.insertBookmarkCrossRef(FolderBookmarkCrossRef(folderId = parentId, componentId = bookmark.id))
        folderDao.updateChangeTime(parentId, System.currentTimeMillis())
        return componentMapper.map(bookmark)
    }

    suspend fun deleteFolder(folderId: Long) {
        deleteFolderRecursive(folderId)
    }

    suspend fun exportRootFolder(): BookmarksFolderViewData {
        val root = folderDao.getById(ROOT_FOLDER_ID) ?: return BookmarksFolderViewData.empty()
        return buildFolderViewData(root)
    }

    suspend fun importFolder(folder: BookmarksFolderViewData, parentId: Long?) {
        folderDao.insert(
            BookmarksFolderEntity(
                id = folder.id,
                name = folder.name,
                parentId = parentId,
                changeTime = folder.changeTime
            )
        )
        folder.bookmarks.forEach { bookmark ->
            val entity = componentMapper.mapToEntity(bookmark)
            componentDao.insert(entity)
            folderDao.insertBookmarkCrossRef(
                FolderBookmarkCrossRef(folderId = folder.id, componentId = entity.id)
            )
        }
        folder.folders.forEach { subfolder -> importFolder(subfolder, folder.id) }
    }

    suspend fun deleteBookmark(componentId: Long, parentFolderId: Long) {
        folderDao.deleteBookmarkCrossRef(parentFolderId, componentId)
        folderDao.deleteAllCrossRefsForComponent(componentId)
        componentDao.deleteById(componentId)
        folderDao.updateChangeTime(parentFolderId, System.currentTimeMillis())
    }

    private suspend fun deleteFolderRecursive(folderId: Long) {
        for (sub in folderDao.getSubfolders(folderId)) {
            deleteFolderRecursive(sub.id)
        }
        for (bookmark in folderDao.getBookmarks(folderId)) {
            componentDao.deleteById(bookmark.id)
        }
        folderDao.deleteById(folderId)
    }

    private suspend fun buildFolderViewData(entity: BookmarksFolderEntity): BookmarksFolderViewData {
        val subfolders = folderDao.getSubfolders(entity.id).map { buildFolderViewData(it) }
        val bookmarks = folderDao.getBookmarks(entity.id)
        val path = buildPath(entity)
        return folderMapper.map(entity, subfolders, bookmarks, path)
    }

    private suspend fun buildPath(entity: BookmarksFolderEntity): String {
        val segments = mutableListOf<String>()
        var current = entity
        while (current.parentId != null) {
            val parent = folderDao.getById(current.parentId!!) ?: break
            segments.add(0, parent.name)
            current = parent
        }
        return segments.joinToString("/")
    }
}
