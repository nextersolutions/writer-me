package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.ComponentViewData
import javax.inject.Inject

interface ToggleCheckboxUseCase {
    suspend operator fun invoke(checkbox: ComponentViewData)
}

class ToggleCheckboxUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : ToggleCheckboxUseCase {
    override suspend fun invoke(checkbox: ComponentViewData) =
        noteRepository.toggleCheckbox(checkbox)
}
