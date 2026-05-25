package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.ComponentViewData
import javax.inject.Inject

interface AddSectionUseCase {
    suspend operator fun invoke(noteId: Long, component: ComponentViewData)
}

class AddSectionUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : AddSectionUseCase {
    override suspend fun invoke(noteId: Long, component: ComponentViewData) =
        noteRepository.addSection(noteId, component)
}
