package io.writerme.app.data.mapper

import io.writerme.app.data.model.SettingsEntity
import io.writerme.app.ui.state.SettingsState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsMapper @Inject constructor() {

    fun mapToState(entity: SettingsEntity): SettingsState = SettingsState(
        fullName = entity.fullName,
        email = entity.email,
        profilePictureUrl = entity.profilePictureUrl,
        currentLanguage = entity.currentLanguage,
        isDarkMode = entity.isDarkMode,
        mediaChanges = entity.mediaChanges,
        voiceChanges = entity.voiceChanges,
        textChanges = entity.textChanges,
        taskChanges = entity.taskChanges,
        linkChanges = entity.linkChanges
    )
}
