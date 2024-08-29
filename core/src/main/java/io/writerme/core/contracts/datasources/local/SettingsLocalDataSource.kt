package io.writerme.core.contracts.datasources.local

import io.writerme.core.models.model.Settings
import java.io.Closeable

interface SettingsLocalDataSource : Closeable {
    suspend fun getSettings(): Settings
    suspend fun saveName(name: String)
    suspend fun updateProfileImage(url: String)
    suspend fun setCounter(key: String, value: Int)
    suspend fun setDarkMode(isDarkMode: Boolean)
    suspend fun setLanguage(language: String)
}