package io.writerme.app.data.repository

import io.writerme.app.data.dao.SettingsDao
import io.writerme.app.data.mapper.SettingsMapper
import io.writerme.app.data.model.SettingsEntity
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.utils.Const
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val settingsDao: SettingsDao,
    private val settingsMapper: SettingsMapper
) : Repository() {

    fun observeSettings(): Flow<SettingsState> = settingsDao.observe()
        .filterNotNull()
        .map { settingsMapper.mapToState(it) }

    suspend fun getSettings(): SettingsEntity? = settingsDao.get()

    suspend fun ensureSettingsExist() {
        if (settingsDao.get() == null) {
            settingsDao.insert(SettingsEntity())
        }
    }

    suspend fun saveName(name: String) {
        val settings = settingsDao.get() ?: return
        settingsDao.update(settings.copy(fullName = name))
    }

    suspend fun updateProfileImage(url: String) {
        val settings = settingsDao.get() ?: return
        settingsDao.update(settings.copy(profilePictureUrl = url))
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        val settings = settingsDao.get() ?: return
        settingsDao.update(settings.copy(isDarkMode = isDarkMode))
    }

    suspend fun setLanguage(language: String) {
        val settings = settingsDao.get() ?: return
        settingsDao.update(settings.copy(currentLanguage = language))
    }

    suspend fun setCounter(key: String, value: Int) {
        val settings = settingsDao.get() ?: return
        val updated = when (key) {
            Const.MEDIA_CHANGES_HISTORY_KEY -> settings.copy(mediaChanges = value)
            Const.VOICE_CHANGES_HISTORY_KEY -> settings.copy(voiceChanges = value)
            Const.TEXT_CHANGES_HISTORY_KEY -> settings.copy(textChanges = value)
            Const.TASK_CHANGES_HISTORY_KEY -> settings.copy(taskChanges = value)
            Const.LINK_CHANGES_HISTORY_KEY -> settings.copy(linkChanges = value)
            else -> return
        }
        settingsDao.update(updated)
    }
}
