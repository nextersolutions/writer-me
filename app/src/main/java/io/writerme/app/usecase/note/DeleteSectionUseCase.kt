package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import javax.inject.Inject

interface DeleteSectionUseCase {
    suspend operator fun invoke(histId: Long)
}

class DeleteSectionUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : DeleteSectionUseCase {
    override suspend fun invoke(histId: Long) =
        noteRepository.deleteSection(histId)
}
