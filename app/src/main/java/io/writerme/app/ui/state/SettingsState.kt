package io.writerme.app.ui.state

import io.writerme.app.utils.Const

data class SettingsState(
    val fullName: String,
    val email: String,
    val profilePictureUrl: String,
    val currentLanguage: String,
    var languages: List<String> = listOf(),
    val isDarkMode: Boolean = true,
    var mediaChanges: Int = Const.MEDIA_CHANGES_HISTORY,
    var voiceChanges: Int = Const.VOICE_CHANGES_HISTORY,
    var textChanges: Int = Const.TEXT_CHANGES_HISTORY,
    var taskChanges: Int = Const.TASK_CHANGES_HISTORY,
    var linkChanges: Int = Const.LINK_CHANGES_HISTORY,
    // export / import
    val isExportSheetVisible: Boolean = false,
    val exportNotes: Boolean = true,
    val exportBookmarks: Boolean = true,
    val isExporting: Boolean = false,
    val isImporting: Boolean = false,
    val dataIoMessage: String? = null
) {

    companion object {
        fun empty(): SettingsState = SettingsState(
            fullName = "",
            email = "",
            profilePictureUrl = "",
            currentLanguage = ""
        )
    }
}