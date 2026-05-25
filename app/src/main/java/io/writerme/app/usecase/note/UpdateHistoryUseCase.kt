package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.ComponentViewData
import javax.inject.Inject

interface UpdateHistoryUseCase {
    suspend operator fun invoke(historyId: Long, component: ComponentViewData)
}

class UpdateHistoryUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : UpdateHistoryUseCase {
    override suspend fun invoke(historyId: Long, component: ComponentViewData) =
        noteRepository.updateHistory(historyId, component)
}
