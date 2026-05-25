package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import javax.inject.Inject

interface AddTagUseCase {
    suspend operator fun invoke(noteId: Long, tag: String)
}

class AddTagUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : AddTagUseCase {
    override suspend fun invoke(noteId: Long, tag: String) =
        noteRepository.addNewTag(noteId, tag)
}
