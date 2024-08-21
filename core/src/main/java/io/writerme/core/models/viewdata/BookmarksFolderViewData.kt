package io.writerme.core.models.viewdata

import io.writerme.core.common.FormatUtils.EMPTY

data class BookmarksFolderViewData(
    val id: String = EMPTY,
    val name: String = EMPTY,
    val folders: List<BookmarksFolderViewData> = emptyList(),
    val bookmarks: List<ComponentViewData> = emptyList(),
    val parent: BookmarksFolderViewData? = null,
    val changeTime: Long = System.currentTimeMillis()
) {
    fun hasParentFolder(): Boolean = parent != null

    val path: String by lazy {
        var fullPath = EMPTY
        var folder: BookmarksFolderViewData? = this
        do {
            if (folder!!.name.isNotEmpty()) {
                fullPath = "/${folder.name}$fullPath"
            }
            folder = folder.parent
        } while (folder != null && folder.hasParentFolder())

        fullPath
    }
}
