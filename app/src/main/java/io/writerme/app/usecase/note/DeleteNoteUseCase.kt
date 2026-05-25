package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import javax.inject.Inject

interface DeleteNoteUseCase {
    suspend operator fun invoke(noteId: Long)
}

class DeleteNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : DeleteNoteUseCase {
    override suspend fun invoke(noteId: Long) =
        noteRepository.deleteNote(noteId)
}
