package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import javax.inject.Inject

interface DeleteTagUseCase {
    suspend operator fun invoke(noteId: Long, tag: String)
}

class DeleteTagUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : DeleteTagUseCase {
    override suspend fun invoke(noteId: Long, tag: String) =
        noteRepository.deleteTag(noteId, tag)
}
