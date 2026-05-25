package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.data.repository.BookmarksRepository
import io.writerme.app.ui.state.BookmarksState
import io.writerme.app.utils.scheduleImageLoading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    private val _bookmarksStateFlow: MutableStateFlow<BookmarksState> =
        MutableStateFlow(BookmarksState.empty())
    val bookmarksStateFlow: StateFlow<BookmarksState> = _bookmarksStateFlow

    private val _currentFolderId = MutableStateFlow(BookmarksRepository.ROOT_FOLDER_ID)

    init {
        viewModelScope.launch {
            bookmarksRepository.ensureRootFolderExists()
        }

        _currentFolderId
            .flatMapLatest { folderId -> bookmarksRepository.observeFolder(folderId) }
            .onEach { folder ->
                _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(currentFolder = folder))
            }
            .launchIn(viewModelScope)
    }

    fun onFolderClicked(folderId: Long) {
        viewModelScope.launch { _currentFolderId.emit(folderId) }
    }

    fun showCreateFolderDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(isFolderDialogDisplayed = true))
        }
    }

    fun dismissCreateFolderDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(isFolderDialogDisplayed = false))
        }
    }

    fun showCreateBookmarkDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(isBookmarkDialogDisplayed = true))
        }
    }

    fun dismissCreateBookmarkDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(isBookmarkDialogDisplayed = false))
        }
    }

    fun toggleFloatingDialog() {
        viewModelScope.launch {
            val value = _bookmarksStateFlow.value
            _bookmarksStateFlow.emit(value.copy(isFloatingDialogShown = !value.isFloatingDialogShown))
        }
    }

    fun navigateToParentFolder() {
        viewModelScope.launch {
            _bookmarksStateFlow.value.currentFolder.parentId?.let { parentId ->
                _currentFolderId.emit(parentId)
            }
        }
    }

    fun createFolder(name: String) {
        viewModelScope.launch {
            bookmarksRepository.createFolder(name, _currentFolderId.value)
        }
    }

    fun createBookmark(url: String, title: String) {
        viewModelScope.launch {
            val bookmark = bookmarksRepository.createBookmark(url, title, _currentFolderId.value)
            workManager.scheduleImageLoading(bookmark.id, _currentFolderId.value)
        }
    }

    fun deleteFolder(folderId: Long) {
        viewModelScope.launch {
            bookmarksRepository.deleteFolder(folderId)
        }
    }

    fun deleteBookmark(componentId: Long) {
        viewModelScope.launch {
            bookmarksRepository.deleteBookmark(componentId, _currentFolderId.value)
        }
    }

    fun toggleFolderDropdown(index: Int) {
        viewModelScope.launch {
            val state = _bookmarksStateFlow.value
            val newValue = if (index >= 0 && state.folderDropdownIndex != index) index else -1
            _bookmarksStateFlow.emit(state.copy(folderDropdownIndex = newValue))
        }
    }

    fun toggleBookmarkDropdown(index: Int) {
        viewModelScope.launch {
            val state = _bookmarksStateFlow.value
            val newValue = if (index >= 0 && state.bookmarkDropdownIndex != index) index else -1
            _bookmarksStateFlow.emit(state.copy(bookmarkDropdownIndex = newValue))
        }
    }
}
