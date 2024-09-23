package io.writerme.features.home.presentation.screens.bookmark

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.core.models.viewdata.BookmarksFolderViewData
import io.writerme.core.models.viewdata.ComponentViewData
import io.writerme.core.presentation.BaseViewModel
import io.writerme.core.presentation.EventHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BookmarksViewModel @Inject constructor(
) : BaseViewModel<State, Action>(), EventHandler<Event> {
    private val stateLock = Object()
    override val initialState: State
        get() = State.Content()

    private fun getContentState(): State.Content {
        synchronized(stateLock) {
            return state.value as State.Content
        }
    }

    private fun updateState(newState: State.Content) {
        synchronized(stateLock) {
            setState(newState)
        }
    }

    init {
        reduceInit()
    }

    private fun reduceInit() {
        setAction(Action.Init)
    }

    override fun obtainEvent(event: Event) {
        when (event) {
            is Event.Init -> reduceInit()
            Event.OnBack -> setAction(Action.Back)
            Event.OnToggleFloatingDialog -> reduceToggleFloatingDialog()
            is Event.OnCreateBookmark -> reduceCreateBookmark(event.url, event.title, event.parent)
            is Event.OnCreateFolder -> reduceCreateFolder(event.name)
            is Event.OnDeleteBookmark -> reduceDeleteBookmark(event.bookmark)
            is Event.OnDeleteFolder -> reduceDeleteFolder(event.folder)
            is Event.OnFolderClick -> reduceFolderClick(event.folder)
            Event.OnNavigateToParentFolder -> reduceNavigateToParent()
            Event.OnToggleCreateBookmarkDialog -> reduceToggleCreateBookmarkDialog()
            Event.OnToggleCreateFolderDialog -> reduceToggleCreateFolderDialog()
            is Event.OnLinkClick -> reduceLinkClick(event.link)
        }
    }

    private fun reduceLinkClick(link: ComponentViewData) {
        // TODO("Not yet implemented")
    }

    private fun reduceToggleCreateFolderDialog() {
        viewModelScope.launch {
            val current = getContentState()
            updateState(
                current.copy(
                    isFolderDialogDisplayed = !current.isFolderDialogDisplayed
                )
            )
        }
    }

    private fun reduceToggleCreateBookmarkDialog() {
        viewModelScope.launch {
            val current = getContentState()
            updateState(
                current.copy(
                    isBookmarkDialogDisplayed = !current.isBookmarkDialogDisplayed
                )
            )
        }
    }

    private fun reduceNavigateToParent() {
        // TODO("Not yet implemented")
    }

    private fun reduceFolderClick(folder: BookmarksFolderViewData) {
        // TODO("Not yet implemented")
    }

    private fun reduceDeleteFolder(folder: BookmarksFolderViewData) {
        //TODO("Not yet implemented")
    }

    private fun reduceDeleteBookmark(bookmark: ComponentViewData) {
        // TODO
    }

    private fun reduceCreateFolder(name: String) {
        //TODO("Not yet implemented")
    }

    private fun reduceCreateBookmark(url: String, title: String, parent: BookmarksFolderViewData?) {
        // TODO("Not yet implemented")
    }

    private fun reduceToggleFloatingDialog() {
        viewModelScope.launch {
            val current = getContentState()
            updateState(
                current.copy(
                    isFloatingDialogShown = !current.isFloatingDialogShown
                )
            )
        }
    }
}
