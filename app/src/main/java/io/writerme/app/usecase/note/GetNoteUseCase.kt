package io.writerme.app.usecase.note

import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.NoteViewData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNoteUseCase {
    operator fun invoke(noteId: Long): Flow<NoteViewData>
}

class GetNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : GetNoteUseCase {
    override fun invoke(noteId: Long): Flow<NoteViewData> = noteRepository.getNote(noteId)
}
