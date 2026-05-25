package io.writerme.app.usecase.bookmarks

import io.writerme.app.data.repository.BookmarksRepository
import javax.inject.Inject

interface EnsureRootFolderUseCase {
    suspend operator fun invoke()
}

class EnsureRootFolderUseCaseImpl @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) : EnsureRootFolderUseCase {
    override suspend fun invoke() = bookmarksRepository.ensureRootFolderExists()
}
