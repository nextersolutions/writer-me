package io.writerme.core.mappers.model

import io.writerme.core.mappers.IBaseEntityMapper
import io.writerme.core.mappers.IBaseMapper
import io.writerme.core.models.model.Settings
import io.writerme.core.models.viewdata.SettingsViewData

class SettingsMapper :
    IBaseEntityMapper<Settings, SettingsViewData>,
    IBaseMapper<SettingsViewData, Settings> {
    override fun toEntity(model: SettingsViewData): Settings {
        return Settings().apply {
            fullName = model.fullName
            email = model.email
            profilePictureUrl = model.profilePictureUrl
            mediaChanges = model.mediaChanges
            voiceChanges = model.voiceChanges
            textChanges = model.textChanges
            taskChanges = model.taskChanges
            linkChanges = model.linkChanges
            currentLanguage = model.currentLanguage
            isDarkMode = model.isDarkMode
        }
    }

    override fun toDomain(model: Settings): SettingsViewData {
        return SettingsViewData(
            fullName = model.fullName,
            email = model.email,
            profilePictureUrl = model.profilePictureUrl,
            mediaChanges = model.mediaChanges,
            voiceChanges = model.voiceChanges,
            textChanges = model.textChanges,
            taskChanges = model.taskChanges,
            linkChanges = model.linkChanges,
            currentLanguage = model.currentLanguage,
            isDarkMode = model.isDarkMode
        )
    }
}
