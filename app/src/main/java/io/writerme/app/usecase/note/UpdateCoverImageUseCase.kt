package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.utils.FilesUtil
import javax.inject.Inject

interface UpdateCoverImageUseCase {
    suspend operator fun invoke(noteId: Long, url: String)
}

class UpdateCoverImageUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository,
    private val filesUtil: FilesUtil
) : UpdateCoverImageUseCase {
    override suspend fun invoke(noteId: Long, url: String) {
        val uri = filesUtil.writeImageToFile(url)
        noteRepository.updateNoteCoverImage(noteId, uri)
    }
}
