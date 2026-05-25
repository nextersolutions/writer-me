package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.app.ui.state.HomeState
import io.writerme.app.usecase.note.DeleteNoteUseCase
import io.writerme.app.usecase.note.GetNotesUseCase
import io.writerme.app.usecase.note.ToggleNoteImportanceUseCase
import io.writerme.app.usecase.settings.ObserveSettingsUseCase
import io.writerme.app.utils.toFirstName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val toggleNoteImportanceUseCase: ToggleNoteImportanceUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _homeStateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeStateFlow: StateFlow<HomeState> = _homeStateFlow

    private val _displayedTab = MutableStateFlow(HomeFilterTab.All)

    init {
        observeSettingsUseCase()
            .onEach { settings ->
                _homeStateFlow.emit(
                    _homeStateFlow.value.copy(
                        firstName = settings.fullName.toFirstName(),
                        profilePhotoUrl = settings.profilePictureUrl
                    )
                )
            }
            .launchIn(viewModelScope)

        combine(getNotesUseCase(), _displayedTab) { notes, tab ->
            val filtered = when (tab) {
                HomeFilterTab.All -> notes
                HomeFilterTab.Important -> notes
                    .filter { it.isImportant }
                    .sortedByDescending { it.changeTime }
            }
            filtered to notes.any { it.isImportant }
        }
            .onEach { (notes, isImportantVisible) ->
                _homeStateFlow.emit(
                    _homeStateFlow.value.copy(
                        notes = notes,
                        isImportantVisible = isImportantVisible
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    fun toggleImportance(noteId: Long) {
        viewModelScope.launch { toggleNoteImportanceUseCase(noteId) }
    }

    fun toggleSearchMode() {
        viewModelScope.launch {
            val current = _homeStateFlow.value
            _homeStateFlow.emit(current.copy(isSearchMode = !current.isSearchMode))
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch { deleteNoteUseCase(noteId) }
    }

    fun onTabChosen(tab: HomeFilterTab) {
        viewModelScope.launch {
            _displayedTab.emit(tab)
            _homeStateFlow.emit(_homeStateFlow.value.copy(chosenTab = tab))
        }
    }

    fun toggleNoteDropdown(index: Int) {
        viewModelScope.launch {
            val current = _homeStateFlow.value
            val newValue = if (current.expandedDropdownId != index) index else -1
            _homeStateFlow.emit(current.copy(expandedDropdownId = newValue))
        }
    }
}
