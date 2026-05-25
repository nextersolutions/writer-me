package io.writerme.app.usecase.bookmarks

import androidx.work.WorkManager
import io.writerme.app.data.repository.BookmarksRepository
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.utils.scheduleImageLoading
import javax.inject.Inject

interface CreateBookmarkUseCase {
    suspend operator fun invoke(url: String, title: String, parentId: Long): ComponentViewData
}

class CreateBookmarkUseCaseImpl @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val workManager: WorkManager
) : CreateBookmarkUseCase {
    override suspend fun invoke(url: String, title: String, parentId: Long): ComponentViewData {
        val bookmark = bookmarksRepository.createBookmark(url, title, parentId)
        workManager.scheduleImageLoading(bookmark.id, parentId)
        return bookmark
    }
}
