package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import javax.inject.Inject

interface ToggleNoteImportanceUseCase {
    suspend operator fun invoke(noteId: Long)
}

class ToggleNoteImportanceUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : ToggleNoteImportanceUseCase {
    override suspend fun invoke(noteId: Long) =
        noteRepository.toggleImportance(noteId)
}
