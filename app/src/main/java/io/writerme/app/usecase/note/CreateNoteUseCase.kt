package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import javax.inject.Inject

interface CreateNoteUseCase {
    suspend operator fun invoke(): Long
}

class CreateNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : CreateNoteUseCase {
    override suspend fun invoke(): Long = noteRepository.createNewNote()
}
