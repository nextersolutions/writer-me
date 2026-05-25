package io.writerme.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.ui.navigation.NoteScreen
import io.writerme.app.ui.state.NoteState
import io.writerme.app.utils.FilesUtil
import io.writerme.app.utils.scheduleImageLoading
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
    private val workManager: WorkManager,
    private val filesUtil: FilesUtil,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val changes: HashMap<Long, Int> = hashMapOf()
    private val pendingUpdates = mutableMapOf<Long, ComponentViewData>()
    private val saveFlow = MutableSharedFlow<ComponentViewData>()

    private val _noteState: MutableStateFlow<NoteState> = MutableStateFlow(NoteState.empty())
    val noteState: StateFlow<NoteState> = _noteState

    init {
        viewModelScope.launch {
            val id: String? = savedState[NoteScreen.NOTE_PARAM]
            val noteId = id?.toLongOrNull() ?: noteRepository.createNewNote()

            noteRepository.getNote(noteId)
                .onEach { updatedNote ->
                    _noteState.emit(_noteState.value.copy(note = updatedNote))
                }
                .launchIn(viewModelScope)
        }

        saveFlow.debounce(300)
            .onEach { component ->
                pendingUpdates.remove(component.id)
                noteRepository.saveComponent(
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
                noteRepository.saveComponent(component)
            } else {
                noteRepository.updateHistory(historyId, component.copy())
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
        viewModelScope.launch {
            noteRepository.addNewCheckBox(_noteState.value.note.id, position)
        }
    }

    fun saveChanges() {
        pendingUpdates.forEach { (k, v) ->
            viewModelScope.launch {
                noteRepository.saveComponent(v)
                pendingUpdates.remove(k)
            }
        }
    }

    fun addImageSection(url: String) {
        viewModelScope.launch {
            val uri = filesUtil.writeImageToFile(url)
            val noteId = _noteState.value.note.id
            val component = ComponentViewData.empty(ComponentType.Image, noteId).copy(mediaUrl = uri)
            noteRepository.addSection(noteId, component)
        }
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
        viewModelScope.launch {
            val noteId = _noteState.value.note.id
            val uri = filesUtil.writeImageToFile(url)
            noteRepository.updateNoteCoverImage(noteId, uri)
        }
    }

    fun showHashtagBar(show: Boolean) {
        viewModelScope.launch {
            _noteState.emit(_noteState.value.copy(isTagsBarVisible = show))
        }
    }

    fun addNewTag(tag: String) {
        viewModelScope.launch {
            noteRepository.addNewTag(_noteState.value.note.id, tag)
        }
    }

    fun deleteTag(tag: String) {
        viewModelScope.launch {
            noteRepository.deleteTag(_noteState.value.note.id, tag)
        }
    }

    fun showDropdown(index: Int) {
        viewModelScope.launch {
            _noteState.emit(_noteState.value.copy(expandedDropdownId = index))
        }
    }

    fun dismissDropDown() {
        viewModelScope.launch {
            _noteState.emit(_noteState.value.copy(expandedDropdownId = -1))
        }
    }

    fun toggleDropDownHistoryMode() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isDropDownInHistoryMode = !state.isDropDownInHistoryMode))
        }
    }

    fun addLinkSection(url: String) {
        viewModelScope.launch {
            val noteId = _noteState.value.note.id
            val component = ComponentViewData.empty(ComponentType.Link, noteId).copy(url = url)
            val saved = noteRepository.saveComponent(component)
            workManager.scheduleImageLoading(saved.id)
            noteRepository.addSection(noteId, saved)
        }
    }

    fun toggleAddLinkDialogVisibility() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isAddLinkDialogDisplayed = !state.isAddLinkDialogDisplayed))
        }
    }

    fun toggleCheckbox(checkbox: ComponentViewData) {
        viewModelScope.launch {
            noteRepository.toggleCheckbox(checkbox)
        }
    }

    fun deleteSection(histId: Long) {
        viewModelScope.launch {
            noteRepository.deleteSection(histId)
        }
    }

    override fun onCleared() {
        saveChanges()
        super.onCleared()
    }

    companion object {
        const val TAG = "NoteViewModel"
    }
}
