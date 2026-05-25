package io.writerme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.writerme.app.utils.Const

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = 0,
    val fullName: String = "",
    val email: String = "",
    val profilePictureUrl: String = "",
    val mediaChanges: Int = Const.MEDIA_CHANGES_HISTORY,
    val voiceChanges: Int = Const.VOICE_CHANGES_HISTORY,
    val textChanges: Int = Const.TEXT_CHANGES_HISTORY,
    val taskChanges: Int = Const.TASK_CHANGES_HISTORY,
    val linkChanges: Int = Const.LINK_CHANGES_HISTORY,
    val currentLanguage: String = Const.SUPPORTED_LANGUAGES[0],
    val isDarkMode: Boolean = true
)
