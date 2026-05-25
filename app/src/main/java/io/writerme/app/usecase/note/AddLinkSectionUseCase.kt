package io.writerme.app.usecase.note

import androidx.work.WorkManager
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.utils.scheduleImageLoading
import javax.inject.Inject

interface AddLinkSectionUseCase {
    suspend operator fun invoke(noteId: Long, url: String)
}

class AddLinkSectionUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository,
    private val workManager: WorkManager
) : AddLinkSectionUseCase {
    override suspend fun invoke(noteId: Long, url: String) {
        val component = ComponentViewData.empty(ComponentType.Link, noteId).copy(url = url)
        val saved = noteRepository.saveComponent(component)
        workManager.scheduleImageLoading(saved.id)
        noteRepository.addSection(noteId, saved)
    }
}
