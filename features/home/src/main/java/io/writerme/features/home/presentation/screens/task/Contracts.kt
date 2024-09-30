package io.writerme.features.home.presentation.screens.task

import io.writerme.core.common.FormatUtils.EMPTY
import java.time.LocalDate

internal sealed class State {
    data class Content(
        val date: LocalDate = LocalDate.now(),
        val description: String = EMPTY
    ) : State()
}

internal sealed class Event {
    data object Init : Event()

}

internal sealed class Action {
    data object Init : Action()
    data object OnNetworkError : Action()
    data class OnError(val message: String? = null) : Action()
}
