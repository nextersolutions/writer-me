package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.NoteViewData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNotesUseCase {
    operator fun invoke(): Flow<List<NoteViewData>>
}

class GetNotesUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : GetNotesUseCase {
    override fun invoke(): Flow<List<NoteViewData>> = noteRepository.getNotes()
}
