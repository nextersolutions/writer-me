package io.writerme.app.usecase.bookmarks

import io.writerme.app.data.repository.BookmarksRepository
import javax.inject.Inject

interface DeleteFolderUseCase {
    suspend operator fun invoke(folderId: Long)
}

class DeleteFolderUseCaseImpl @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) : DeleteFolderUseCase {
    override suspend fun invoke(folderId: Long) =
        bookmarksRepository.deleteFolder(folderId)
}
