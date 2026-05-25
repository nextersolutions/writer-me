package io.writerme.app.data.viewdata

data class BookmarksFolderViewData(
    val id: Long,
    val name: String,
    val path: String,
    val folders: List<BookmarksFolderViewData>,
    val bookmarks: List<ComponentViewData>,
    val parentId: Long?,
    val changeTime: Long
) {
    fun hasParentFolder(): Boolean = parentId != null

    companion object {
        fun empty(): BookmarksFolderViewData = BookmarksFolderViewData(
            id = 0,
            name = "",
            path = "",
            folders = emptyList(),
            bookmarks = emptyList(),
            parentId = null,
            changeTime = 0
        )
    }
}
