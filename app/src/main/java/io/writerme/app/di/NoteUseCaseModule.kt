package io.writerme.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.writerme.app.usecase.note.AddCheckBoxUseCase
import io.writerme.app.usecase.note.AddCheckBoxUseCaseImpl
import io.writerme.app.usecase.note.AddImageSectionUseCase
import io.writerme.app.usecase.note.AddImageSectionUseCaseImpl
import io.writerme.app.usecase.note.AddLinkSectionUseCase
import io.writerme.app.usecase.note.AddLinkSectionUseCaseImpl
import io.writerme.app.usecase.note.AddSectionUseCase
import io.writerme.app.usecase.note.AddSectionUseCaseImpl
import io.writerme.app.usecase.note.AddTagUseCase
import io.writerme.app.usecase.note.AddTagUseCaseImpl
import io.writerme.app.usecase.note.CreateNoteUseCase
import io.writerme.app.usecase.note.CreateNoteUseCaseImpl
import io.writerme.app.usecase.note.DeleteNoteUseCase
import io.writerme.app.usecase.note.DeleteNoteUseCaseImpl
import io.writerme.app.usecase.note.DeleteSectionUseCase
import io.writerme.app.usecase.note.DeleteSectionUseCaseImpl
import io.writerme.app.usecase.note.DeleteTagUseCase
import io.writerme.app.usecase.note.DeleteTagUseCaseImpl
import io.writerme.app.usecase.note.GetNoteUseCase
import io.writerme.app.usecase.note.GetNoteUseCaseImpl
import io.writerme.app.usecase.note.GetNotesUseCase
import io.writerme.app.usecase.note.GetNotesUseCaseImpl
import io.writerme.app.usecase.note.SaveComponentUseCase
import io.writerme.app.usecase.note.SaveComponentUseCaseImpl
import io.writerme.app.usecase.note.ToggleCheckboxUseCase
import io.writerme.app.usecase.note.ToggleCheckboxUseCaseImpl
import io.writerme.app.usecase.note.ToggleNoteImportanceUseCase
import io.writerme.app.usecase.note.ToggleNoteImportanceUseCaseImpl
import io.writerme.app.usecase.note.UpdateCoverImageUseCase
import io.writerme.app.usecase.note.UpdateCoverImageUseCaseImpl
import io.writerme.app.usecase.note.UpdateHistoryUseCase
import io.writerme.app.usecase.note.UpdateHistoryUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteUseCaseModule {

    @Binds abstract fun bindGetNotes(impl: GetNotesUseCaseImpl): GetNotesUseCase
    @Binds abstract fun bindGetNote(impl: GetNoteUseCaseImpl): GetNoteUseCase
    @Binds abstract fun bindCreateNote(impl: CreateNoteUseCaseImpl): CreateNoteUseCase
    @Binds abstract fun bindSaveComponent(impl: SaveComponentUseCaseImpl): SaveComponentUseCase
    @Binds abstract fun bindUpdateHistory(impl: UpdateHistoryUseCaseImpl): UpdateHistoryUseCase
    @Binds abstract fun bindUpdateCoverImage(impl: UpdateCoverImageUseCaseImpl): UpdateCoverImageUseCase
    @Binds abstract fun bindAddCheckBox(impl: AddCheckBoxUseCaseImpl): AddCheckBoxUseCase
    @Binds abstract fun bindAddSection(impl: AddSectionUseCaseImpl): AddSectionUseCase
    @Binds abstract fun bindAddTag(impl: AddTagUseCaseImpl): AddTagUseCase
    @Binds abstract fun bindDeleteTag(impl: DeleteTagUseCaseImpl): DeleteTagUseCase
    @Binds abstract fun bindToggleCheckbox(impl: ToggleCheckboxUseCaseImpl): ToggleCheckboxUseCase
    @Binds abstract fun bindDeleteSection(impl: DeleteSectionUseCaseImpl): DeleteSectionUseCase
    @Binds abstract fun bindToggleNoteImportance(impl: ToggleNoteImportanceUseCaseImpl): ToggleNoteImportanceUseCase
    @Binds abstract fun bindDeleteNote(impl: DeleteNoteUseCaseImpl): DeleteNoteUseCase
    @Binds abstract fun bindAddLinkSection(impl: AddLinkSectionUseCaseImpl): AddLinkSectionUseCase
    @Binds abstract fun bindAddImageSection(impl: AddImageSectionUseCaseImpl): AddImageSectionUseCase
}
