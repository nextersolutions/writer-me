package io.writerme.core.mappers.model

import io.writerme.core.extensions.toRealmList
import io.writerme.core.mappers.IBaseEntityMapper
import io.writerme.core.mappers.IBaseMapper
import io.writerme.core.models.model.BookmarksFolder
import io.writerme.core.models.viewdata.BookmarksFolderViewData
import javax.inject.Inject

class BookmarkFolderMapper @Inject constructor(
    private val componentMapper: ComponentMapper
) : IBaseEntityMapper<BookmarksFolder, BookmarksFolderViewData>,
    IBaseMapper<BookmarksFolderViewData, BookmarksFolder> {
    override fun toEntity(model: BookmarksFolderViewData): BookmarksFolder {
        return BookmarksFolder().apply {
            id = model.id
            name = model.name
            folders = model.folders.map { this@BookmarkFolderMapper.toEntity(it) }.toRealmList()
            bookmarks = model.bookmarks.map { componentMapper.toEntity(it) }.toRealmList()
            parent = if (model.parent != null) {
                this@BookmarkFolderMapper.toEntity(model.parent)
            } else {
                null
            }
            changeTime = model.changeTime
        }
    }

    override fun toDomain(model: BookmarksFolder): BookmarksFolderViewData {
        return BookmarksFolderViewData(
            id = model.id,
            name = model.name,
            folders = model.folders.toList().map { this@BookmarkFolderMapper.toDomain(it) },
            bookmarks = model.bookmarks.toList().map { componentMapper.toDomain(it) },
            parent = if (model.parent != null) this.toDomain(model.parent!!) else null,
            changeTime = model.changeTime
        )
    }
}
