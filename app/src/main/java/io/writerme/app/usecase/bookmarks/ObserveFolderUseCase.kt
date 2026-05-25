package io.writerme.app.usecase.bookmarks

import io.writerme.app.data.repository.BookmarksRepository
import io.writerme.app.data.viewdata.BookmarksFolderViewData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveFolderUseCase {
    operator fun invoke(folderId: Long): Flow<BookmarksFolderViewData>
}

class ObserveFolderUseCaseImpl @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) : ObserveFolderUseCase {
    override fun invoke(folderId: Long): Flow<BookmarksFolderViewData> =
        bookmarksRepository.observeFolder(folderId)
}
