package io.writerme.features.home.presentation.screens.bookmark

import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.core.presentation.BaseViewModel
import io.writerme.core.presentation.EventHandler
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
            Event.OnBack -> TODO()
            Event.OnToggleFloatingDialog -> TODO()
        }
    }
}
