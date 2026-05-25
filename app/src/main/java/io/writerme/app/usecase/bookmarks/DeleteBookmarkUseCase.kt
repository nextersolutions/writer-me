package io.writerme.app.usecase.bookmarks

import io.writerme.app.data.repository.BookmarksRepository
import javax.inject.Inject

interface DeleteBookmarkUseCase {
    suspend operator fun invoke(componentId: Long, parentFolderId: Long)
}

class DeleteBookmarkUseCaseImpl @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) : DeleteBookmarkUseCase {
    override suspend fun invoke(componentId: Long, parentFolderId: Long) =
        bookmarksRepository.deleteBookmark(componentId, parentFolderId)
}
