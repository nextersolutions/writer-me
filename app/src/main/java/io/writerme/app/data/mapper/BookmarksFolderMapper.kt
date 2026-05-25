package io.writerme.app.data.mapper

import io.writerme.app.data.model.BookmarksFolderEntity
import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.viewdata.BookmarksFolderViewData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarksFolderMapper @Inject constructor(
    private val componentMapper: ComponentMapper
) {
    fun map(
        entity: BookmarksFolderEntity,
        subfolders: List<BookmarksFolderViewData>,
        bookmarks: List<ComponentEntity>,
        path: String
    ): BookmarksFolderViewData = BookmarksFolderViewData(
        id = entity.id,
        name = entity.name,
        path = path,
        folders = subfolders,
        bookmarks = bookmarks.map { componentMapper.map(it) },
        parentId = entity.parentId,
        changeTime = entity.changeTime
    )
}
