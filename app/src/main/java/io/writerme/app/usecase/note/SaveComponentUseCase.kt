package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.ComponentViewData
import javax.inject.Inject

interface SaveComponentUseCase {
    suspend operator fun invoke(component: ComponentViewData): ComponentViewData
}

class SaveComponentUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : SaveComponentUseCase {
    override suspend fun invoke(component: ComponentViewData): ComponentViewData =
        noteRepository.saveComponent(component)
}
