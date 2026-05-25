package io.writerme.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.ui.navigation.NoteScreen
import io.writerme.app.ui.state.NoteState
import io.writerme.app.usecase.note.AddCheckBoxUseCase
import io.writerme.app.usecase.note.AddImageSectionUseCase
import io.writerme.app.usecase.note.AddLinkSectionUseCase
import io.writerme.app.usecase.note.AddSectionUseCase
import io.writerme.app.usecase.note.AddTagUseCase
import io.writerme.app.usecase.note.CreateNoteUseCase
import io.writerme.app.usecase.note.DeleteSectionUseCase
import io.writerme.app.usecase.note.DeleteTagUseCase
import io.writerme.app.usecase.note.GetNoteUseCase
import io.writerme.app.usecase.note.SaveComponentUseCase
import io.writerme.app.usecase.note.ToggleCheckboxUseCase
import io.writerme.app.usecase.note.UpdateCoverImageUseCase
import io.writerme.app.usecase.note.UpdateHistoryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val createNoteUseCase: CreateNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val saveComponentUseCase: SaveComponentUseCase,
    private val updateHistoryUseCase: UpdateHistoryUseCase,
    private val updateCoverImageUseCase: UpdateCoverImageUseCase,
    private val addCheckBoxUseCase: AddCheckBoxUseCase,
    private val addSectionUseCase: AddSectionUseCase,
    private val addTagUseCase: AddTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
    private val toggleCheckboxUseCase: ToggleCheckboxUseCase,
    private val deleteSectionUseCase: DeleteSectionUseCase,
    private val addLinkSectionUseCase: AddLinkSectionUseCase,
    private val addImageSectionUseCase: AddImageSectionUseCase,
) : ViewModel() {

    private val changes: HashMap<Long, Int> = hashMapOf()
    private val pendingUpdates = mutableMapOf<Long, ComponentViewData>()
    private val saveFlow = MutableSharedFlow<ComponentViewData>()

    private val _noteState: MutableStateFlow<NoteState> = MutableStateFlow(NoteState.empty())
    val noteState: StateFlow<NoteState> = _noteState

    init {
        viewModelScope.launch {
            val id: String? = savedState[NoteScreen.NOTE_PARAM]
            val noteId = id?.toLongOrNull() ?: createNoteUseCase()

            getNoteUseCase(noteId)
                .onEach { updatedNote ->
                    _noteState.emit(_noteState.value.copy(note = updatedNote))
                }
                .launchIn(viewModelScope)
        }

        saveFlow.debounce(300)
            .onEach { component ->
                pendingUpdates.remove(component.id)
                saveComponentUseCase(
                    component.copy(
                        content = component.content.trimEnd { it.isWhitespace() || it == '\n' }
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    fun modifyHistory(historyId: Long, component: ComponentViewData) {
        val change = changes.getOrDefault(component.id, 0)
        viewModelScope.launch {
            if (change > 0) {
                saveComponentUseCase(component)
            } else {
                updateHistoryUseCase(historyId, component.copy())
                changes[component.id] = 1
            }
        }
    }

    fun onComponentChange(component: ComponentViewData) {
        if (component.noteId > 0) {
            viewModelScope.launch {
                pendingUpdates[component.id] = component
                saveFlow.emit(component)
            }
        }
    }

    fun addNewCheckBox(position: Int) {
        viewModelScope.launch { addCheckBoxUseCase(_noteState.value.note.id, position) }
    }

    fun saveChanges() {
        pendingUpdates.forEach { (k, v) ->
            viewModelScope.launch {
                saveComponentUseCase(v)
                pendingUpdates.remove(k)
            }
        }
    }

    fun addImageSection(url: String) {
        viewModelScope.launch { addImageSectionUseCase(_noteState.value.note.id, url) }
    }

    fun toggleHistoryMode() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isHistoryMode = !state.isHistoryMode))
        }
    }

    fun toggleTopBarDropdownVisibility() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isTopBarDropdownVisible = !state.isTopBarDropdownVisible))
        }
    }

    fun updateCoverImage(url: String) {
        viewModelScope.launch { updateCoverImageUseCase(_noteState.value.note.id, url) }
    }

    fun showHashtagBar(show: Boolean) {
        viewModelScope.launch { _noteState.emit(_noteState.value.copy(isTagsBarVisible = show)) }
    }

    fun addNewTag(tag: String) {
        viewModelScope.launch { addTagUseCase(_noteState.value.note.id, tag) }
    }

    fun deleteTag(tag: String) {
        viewModelScope.launch { deleteTagUseCase(_noteState.value.note.id, tag) }
    }

    fun showDropdown(index: Int) {
        viewModelScope.launch { _noteState.emit(_noteState.value.copy(expandedDropdownId = index)) }
    }

    fun dismissDropDown() {
        viewModelScope.launch { _noteState.emit(_noteState.value.copy(expandedDropdownId = -1)) }
    }

    fun toggleDropDownHistoryMode() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isDropDownInHistoryMode = !state.isDropDownInHistoryMode))
        }
    }

    fun addLinkSection(url: String) {
        viewModelScope.launch { addLinkSectionUseCase(_noteState.value.note.id, url) }
    }

    fun toggleAddLinkDialogVisibility() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isAddLinkDialogDisplayed = !state.isAddLinkDialogDisplayed))
        }
    }

    fun toggleCheckbox(checkbox: ComponentViewData) {
        viewModelScope.launch { toggleCheckboxUseCase(checkbox) }
    }

    fun deleteSection(histId: Long) {
        viewModelScope.launch { deleteSectionUseCase(histId) }
    }

    override fun onCleared() {
        saveChanges()
        super.onCleared()
    }

    companion object {
        const val TAG = "NoteViewModel"
    }
}
