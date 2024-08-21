package io.writerme.core.models.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.core.common.GlobalConstants
import io.writerme.core.common.GlobalConstants.HistoryDefaults

open class Settings : RealmObject {
    @Index
    @PrimaryKey
    var id = ZERO

    var fullName: String = EMPTY

    var email: String = EMPTY

    var profilePictureUrl: String = EMPTY

    var mediaChanges: Int = HistoryDefaults.MEDIA_CHANGES_HISTORY
    var voiceChanges: Int = HistoryDefaults.VOICE_CHANGES_HISTORY
    var textChanges: Int = HistoryDefaults.TEXT_CHANGES_HISTORY
    var taskChanges: Int = HistoryDefaults.TASK_CHANGES_HISTORY
    var linkChanges: Int = HistoryDefaults.LINK_CHANGES_HISTORY

    var currentLanguage: String = GlobalConstants.SUPPORTED_LANGUAGES.first()

    var isDarkMode: Boolean = true

    fun setHistory(key: String, value: Int) {
        when (key) {
            HistoryDefaults.MEDIA_CHANGES_HISTORY_KEY -> this.mediaChanges = value
            HistoryDefaults.VOICE_CHANGES_HISTORY_KEY -> this.voiceChanges = value
            HistoryDefaults.TEXT_CHANGES_HISTORY_KEY -> this.textChanges = value
            HistoryDefaults.TASK_CHANGES_HISTORY_KEY -> this.taskChanges = value
            HistoryDefaults.LINK_CHANGES_HISTORY_KEY -> this.linkChanges = value
        }
    }
}
