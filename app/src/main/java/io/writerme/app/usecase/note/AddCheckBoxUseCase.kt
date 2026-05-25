package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import javax.inject.Inject

interface AddCheckBoxUseCase {
    suspend operator fun invoke(noteId: Long, position: Int)
}

class AddCheckBoxUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : AddCheckBoxUseCase {
    override suspend fun invoke(noteId: Long, position: Int) =
        noteRepository.addNewCheckBox(noteId, position)
}
