package io.writerme.features.home.presentation.screens.settings

import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.GlobalConstants
import io.writerme.core.common.GlobalConstants.HistoryDefaults

internal sealed class State {
    data class Content(
        val fullName: String = EMPTY,
        val email: String = EMPTY,
        val profilePictureUrl: String = EMPTY,
        val currentLanguage: String = GlobalConstants.SUPPORTED_LANGUAGES.first(),
        var languages: List<String> = emptyList(),
        val isDarkMode: Boolean = true,
        var mediaChanges: Int = HistoryDefaults.MEDIA_CHANGES_HISTORY,
        var voiceChanges: Int = HistoryDefaults.VOICE_CHANGES_HISTORY,
        var textChanges: Int = HistoryDefaults.TEXT_CHANGES_HISTORY,
        var taskChanges: Int = HistoryDefaults.TASK_CHANGES_HISTORY,
        var linkChanges: Int = HistoryDefaults.LINK_CHANGES_HISTORY
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
