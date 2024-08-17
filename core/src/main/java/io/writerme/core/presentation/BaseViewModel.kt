package io.writerme.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.writerme.core.contracts.usecases.event.IAnalyticsEventUseCase
import io.writerme.core.models.event.AnalyticsEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, A>(private val analyticsEventUseCase: IAnalyticsEventUseCase? = null) :
    ViewModel() {

    abstract val initialState: S

    private val mutableState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = mutableState.asStateFlow()

    private val _action = Channel<A>(capacity = Channel.BUFFERED)
    val action: Flow<A> get() = _action.receiveAsFlow()

    protected fun setAction(newAction: A) {
        _action.trySend(newAction)
    }

    protected fun setState(newState: S) {
        mutableState.value = newState
    }

    protected fun sendAnalyticsEvent(event: AnalyticsEvent) =
        viewModelScope.launch {
            analyticsEventUseCase?.sendEvent(event)
        }
}
