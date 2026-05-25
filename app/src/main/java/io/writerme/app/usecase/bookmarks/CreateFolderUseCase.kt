package io.writerme.app.usecase.bookmarks

import io.writerme.app.data.repository.BookmarksRepository
import javax.inject.Inject

interface CreateFolderUseCase {
    suspend operator fun invoke(name: String, parentId: Long)
}

class CreateFolderUseCaseImpl @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) : CreateFolderUseCase {
    override suspend fun invoke(name: String, parentId: Long) =
        bookmarksRepository.createFolder(name, parentId)
}
