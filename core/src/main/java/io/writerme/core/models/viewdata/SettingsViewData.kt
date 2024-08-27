package io.writerme.core.models.viewdata

import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.GlobalConstants

data class SettingsViewData(
    val fullName: String = EMPTY,
    val email: String = EMPTY,
    val profilePictureUrl: String = EMPTY,
    val mediaChanges: Int = GlobalConstants.HistoryDefaults.MEDIA_CHANGES_HISTORY,
    val voiceChanges: Int = GlobalConstants.HistoryDefaults.VOICE_CHANGES_HISTORY,
    val textChanges: Int = GlobalConstants.HistoryDefaults.TEXT_CHANGES_HISTORY,
    val taskChanges: Int = GlobalConstants.HistoryDefaults.TASK_CHANGES_HISTORY,
    val linkChanges: Int = GlobalConstants.HistoryDefaults.LINK_CHANGES_HISTORY,
    val currentLanguage: String = GlobalConstants.SUPPORTED_LANGUAGES.first(),
    val isDarkMode: Boolean = true,
    var languages: List<String> = listOf()
)
