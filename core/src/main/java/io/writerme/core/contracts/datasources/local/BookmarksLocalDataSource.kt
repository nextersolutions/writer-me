package io.writerme.core.contracts.datasources.local

import io.writerme.core.models.model.BookmarksFolder
import io.writerme.core.models.model.Component

interface BookmarksLocalDataSource : BaseLocalDataSource {
    suspend fun getMainFolder(): BookmarksFolder
    suspend fun getLatest(folder: BookmarksFolder): BookmarksFolder?
    suspend fun createFolder(
        name: String,
        parent: BookmarksFolder? = null
    )

    suspend fun createBookmark(
        url: String,
        title: String,
        parent: BookmarksFolder? = null
    ): Component

    suspend fun deleteFolder(bookmarksFolder: BookmarksFolder)

    suspend fun deleteBookmark(component: Component)
}