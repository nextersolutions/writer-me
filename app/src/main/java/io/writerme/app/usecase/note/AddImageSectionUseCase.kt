package io.writerme.app.usecase.note

import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.utils.FilesUtil
import javax.inject.Inject

interface AddImageSectionUseCase {
    suspend operator fun invoke(noteId: Long, url: String)
}

class AddImageSectionUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository,
    private val filesUtil: FilesUtil
) : AddImageSectionUseCase {
    override suspend fun invoke(noteId: Long, url: String) {
        val uri = filesUtil.writeImageToFile(url)
        val component = ComponentViewData.empty(ComponentType.Image, noteId).copy(mediaUrl = uri)
        noteRepository.addSection(noteId, component)
    }
}
